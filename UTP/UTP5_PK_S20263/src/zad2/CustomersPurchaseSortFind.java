/**
 *
 *  @author Paklepa Kacper S20263
 *
 */

package zad2;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class CustomersPurchaseSortFind {
    private ArrayList<Purchase> purchaseArray = new ArrayList<>();

    public CustomersPurchaseSortFind(){
    }
    public void readFile(String fname){
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(fname));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (true) {
            assert scanner != null;
            if (!scanner.hasNext()) break;
            String next = scanner.nextLine();
            String [] tmp =  next.split(";");
            purchaseArray.add(new Purchase(tmp[0],tmp[1],tmp[2],Double.parseDouble(tmp[3]),Double.parseDouble(tmp[4])));
        }
        scanner.close();
    }
    public void showSortedBy(String category){
        if(Objects.equals(category, "Nazwiska")){
            System.out.println("Nazwiska");
            int n = purchaseArray.size();
            Purchase tmp;
            for(int i = 0; i < n-1; i++){
                for(int j = 0; j < n-i-1;j++){
                    if(purchaseArray.get(j).compareByName(purchaseArray.get(j+1))) {
                        tmp = purchaseArray.get(j);
                        purchaseArray.set(j, purchaseArray.get(j + 1));
                        purchaseArray.set(j + 1, tmp);
                    }
                }
            }
            for (Purchase purchase : purchaseArray) {
                System.out.println(purchase.toString());
            }
            System.out.println();
        }

        if(Objects.equals(category,"Koszty")){
            System.out.println("Koszty");
            int n = purchaseArray.size();
            Purchase tmp;
            for(int i = 0; i < n-1; i++){
                for(int j = 0; j < n-i-1;j++){
                    if(purchaseArray.get(j).compareByCost(purchaseArray.get(j+1))) {
                        tmp = purchaseArray.get(j);
                        purchaseArray.set(j, purchaseArray.get(j + 1));
                        purchaseArray.set(j + 1, tmp);
                    }
                }
            }
            for (Purchase purchase : purchaseArray) {
                System.out.println(purchase.toString() + "(koszt: " + purchase.getKoszt() + ")");
            }
            System.out.println();
        }
    }
    public void showPurchaseFor(String id){
        if(Objects.equals(id, "c00001")){
            System.out.println("Klient c00001");
            for (Purchase purchase : purchaseArray) {
                if (Objects.equals(purchase.getId(), id)) {
                    System.out.println(purchase);
                }
            }
            System.out.println();
        }
        else if (Objects.equals(id,"c00002")){
            System.out.println("Klient c00002");
            for (Purchase purchase : purchaseArray) {
                if (Objects.equals(purchase.getId(), id)) {
                    System.out.println(purchase);
                }
            }
            System.out.println();
        }
    }
}
