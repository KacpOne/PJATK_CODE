package zad2;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Futil {

    public static void processDir(String dirName, String resultFileName) {

        Path dir = Paths.get(dirName);
        ArrayList<String> text = new ArrayList<>();
        List<Path> paths;
        try {
            paths = listFiles(dir);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(resultFileName), StandardCharsets.UTF_8));
            paths.forEach(e -> execute(e, text));
            text.forEach(x -> {
                try {
                    out.write(x);
                    out.newLine();
                } catch (IOException ignored) {
                }
            });
            out.close();
        } catch (IOException ignored) {}
    }

    public static List<Path> listFiles(Path dirName) throws IOException {

        List<Path> result;
        try (Stream<Path> walk = Files.walk(dirName)) {
            result = walk.filter(Files::isRegularFile).collect(Collectors.toList());
        }
        return result;
    }

    public static void execute(Path dirName, ArrayList<String> text) {
        try {
            Scanner in = new Scanner(dirName, "Cp1250");
            while (in.hasNext()) {
                text.add(in.nextLine());
            }
            in.close();
        } catch (IOException ignored) {}
    }
}