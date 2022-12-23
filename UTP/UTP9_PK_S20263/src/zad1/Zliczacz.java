package zad1;

import java.util.ArrayList;

public class Zliczacz extends Thread {

    private long suma;
    private ArrayList<Towar> ar;
    private Klucz klucz;

    public Zliczacz(ArrayList<Towar> ar, Klucz klucz) {
        this.suma = 0;
        this.ar = ar;
        this.klucz = klucz;
    }

    public void zlicz() throws InterruptedException {
        for (int i = 0; i < 10000; i++) {
            while (klucz.getKlucz()) {
                Thread.sleep(0, 1);
            }
            suma += ar.get(i).getWaga();
            if ((ar.get(i).getthisId() % 100) == 0) {
                System.out.println("policzono wage " + ar.get(i).getthisId() + " towarów");
            }
            if ((ar.get(i).getthisId() % 200) == 0) {
                klucz.setKlucz(true);
            }
        }
        System.out.println(suma);
    }

    @Override
    public void run() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            zlicz();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}