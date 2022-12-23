/**
 * @author Paklepa Kacper S20263
 */

package zad1;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args){
        ArrayList<Towar> ar = new ArrayList<>();
        Klucz klucz = new Klucz();
        Utwarzacz utwarzacz = new Utwarzacz(ar, klucz);
        Zliczacz zliczacz = new Zliczacz(ar, klucz);
        utwarzacz.start();
        zliczacz.start();
    }
}