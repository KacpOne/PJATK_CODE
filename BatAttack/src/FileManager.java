package batattack;

import java.io.*;
import java.util.Scanner;

public class FileManager {
    private Scanner scanner;

    public FileManager() {
    }

    public double scanFile(String file) throws IOException {
        try {
            scanner = new Scanner(new File(file));
            return Double.parseDouble(scanner.next());
        } catch (FileNotFoundException e) {
            printFile(file, 100.0);
            scanner = new Scanner(new File(file));
            return Double.parseDouble(scanner.next());
        }
    }

    public void closeFile() {
        scanner.close();
    }

    public void printFile(String file, double time) throws IOException {
        File file1 = new File(file);
        FileWriter fw = new FileWriter(file1);
        PrintWriter pw = new PrintWriter(fw);
        pw.println(time);
        pw.close();
    }
}
