package zad2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Futil {
    public static void processDir(String dirName, String resultFileName) {
        try {
            Path start = Paths.get(dirName);
            FV fv = new FV(Paths.get("./" + resultFileName));
            Files.walkFileTree(start, fv);
            fv.out.close();
        } catch (IOException ignored) {}
    }
}