package zad1;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Utwarzacz extends Thread {

    private ArrayList<Towar> ar;
    private Klucz klucz;

    public Utwarzacz(ArrayList<Towar> ar, Klucz klucz) {
        this.ar = ar;
        this.klucz = klucz;
    }

    public void getFromFile(String filename, ArrayList<Towar> ar) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(filename));
        String tmp;
        String[] tmp1;
        while (sc.hasNext()) {
            if (klucz.getKlucz()) {
                tmp = sc.nextLine();
                tmp1 = tmp.split(" ");
                ar.add(new Towar(Integer.parseInt(tmp1[0]), Integer.parseInt(tmp1[1])));
                if ((Integer.parseInt(tmp1[0]) % 200) == 0) {
                    System.out.println("utworzono " + tmp1[0] + " obiektów");
                    klucz.setKlucz(false);
                }
            }
        }
    }

    @Override
    public void run() {
        try {
            getFromFile("../Towary.txt", ar);
        } catch (FileNotFoundException ignored) {
        }
    }
}
