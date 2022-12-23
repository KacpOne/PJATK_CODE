/**
 * @author Paklepa Kacper S20263
 */

package zad2;


import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    JFrame frame = new JFrame("Sklep");
    JList<Owoc> jl = new JList<>();
    DefaultListModel<Owoc> dfm = new DefaultListModel<>();

    public Main(){

        ExecutorService executorService = Executors.newCachedThreadPool();

        Owoc Jablka = new Owoc(50, 2, "Jablka");
        Owoc Pomarancze = new Owoc(50, 4, "Pomarancze");
        Owoc Banany = new Owoc(50, 6, "Banany");

        dfm.addElement(Jablka);
        dfm.addElement(Banany);
        dfm.addElement(Pomarancze);
        jl.setModel(dfm);


        JButton sprzedawaj = new JButton("Sprzedawaj");
        sprzedawaj.addActionListener((e) -> {

            Owoc owoc = jl.getSelectedValue();
            executorService.submit(owoc);
        });

        JButton stop = new JButton("Stop");
        stop.addActionListener((e) -> {

            Owoc owoc = jl.getSelectedValue();
            owoc.stop();
        });

        JButton stan = new JButton("Stan");
        stan.addActionListener((e) -> {

            Owoc owoc = jl.getSelectedValue();
            System.out.println(owoc.getStan().toString());
        });

        JButton zarobki = new JButton("Zarobki");
        zarobki.addActionListener((e) -> {

            Owoc owoc = jl.getSelectedValue();
            System.out.println("Zarobiono: " + owoc.policzZarobek() + " za " + owoc.getNazwa());
        });


        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.add(jl);
        frame.pack();
        frame.add(sprzedawaj);
        frame.add(stan);
        frame.add(stop);
        frame.add(zarobki);
        frame.pack();
    }
    public static void main(String[] args){
            SwingUtilities.invokeLater(Main::new);
    }
}