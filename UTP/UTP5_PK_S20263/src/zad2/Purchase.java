/**
 * @author Paklepa Kacper S20263
 */

package zad2;

import java.util.Arrays;

public class Purchase {
    private String id;
    private String nazwisko;
    private String nazwaTowaru;
    private double cena;
    private double zakupionaIlosc;

    public Purchase(String id, String nazwisko, String nazwaTowaru, double cena, double zakupionaIlosc) {
        this.id = id;
        this.nazwisko = nazwisko;
        this.nazwaTowaru = nazwaTowaru;
        this.cena = cena;
        this.zakupionaIlosc = zakupionaIlosc;
    }

    public String getId() {
        return id;
    }

    public int getIdInt() {
        String tmp = this.id.substring(1);
        while (tmp.charAt(0) == 0) {
            tmp = tmp.substring(1);
        }
        return Integer.parseInt(tmp);
    }

    public String getNazwisko() {
        String out[] = nazwisko.split(" ");
        return out[0];
    }

    public String getNazwaTowaru() {
        return nazwaTowaru;
    }

    public double getCena() {
        return cena;
    }

    public double getZakupionaIlosc() {
        return zakupionaIlosc;
    }

    public double getKoszt() {
        return this.cena * this.zakupionaIlosc;
    }

    public boolean compareByName(Purchase purchase) {
        if (this.getNazwisko().equals(purchase.getNazwisko())) {
            if (this.getIdInt() >= purchase.getIdInt()) {
                return true;
            } else {
                return false;
            }
        } else if (this.getNazwisko().startsWith(purchase.getNazwisko())) {
            return true;
        } else if (purchase.getNazwisko().startsWith(this.getNazwisko())) {
            return false;
        } else {
            int i = 0;
            do {
                if (purchase.getNazwisko().charAt(i) < this.getNazwisko().charAt(i)) {
                    return true;
                } else if (purchase.getNazwisko().charAt(i) > this.getNazwisko().charAt(i)) {
                    return false;
                }
            } while (purchase.getNazwisko().charAt(i) == this.getNazwisko().charAt(i));
        }
        return false;
    }
    public boolean compareByCost(Purchase purchase){
        if(this.getKoszt()==purchase.getKoszt()){
            if (this.getIdInt() >= purchase.getIdInt()) {
                return true;
            } else {
                return false;
            }
        }
        else {
            if(this.getKoszt()>purchase.getKoszt()){
                return false;
            }
            else{
                return true;
            }
        }
    }

    @Override
    public String toString() {
        return id + ";" + nazwisko + ";" + nazwaTowaru + ";" + cena + ";" + zakupionaIlosc;
    }
}
