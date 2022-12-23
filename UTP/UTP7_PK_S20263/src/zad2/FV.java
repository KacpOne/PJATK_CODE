package zad2;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Scanner;

public class FV implements FileVisitor<Path> {
    public BufferedWriter out;
    public Scanner in;

    public FV(Path path) throws IOException {
        out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(String.valueOf(path)), StandardCharsets.UTF_8));
    }

    @Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes bfa) throws IOException {
        in = new Scanner(Paths.get(String.valueOf(path)),"Cp1250");

        while (in.hasNext()){
            out.write(in.nextLine());
            out.newLine();
        }

        in.close();

        return FileVisitResult.CONTINUE;
    }
    @Override
    public FileVisitResult visitFileFailed(Path path, IOException e) {
        return FileVisitResult.CONTINUE;
    }
    @Override
    public FileVisitResult postVisitDirectory(Path path, IOException e) {
        return FileVisitResult.CONTINUE;
    }
    @Override
    public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes bfa) {
        return FileVisitResult.CONTINUE;
    }
}
