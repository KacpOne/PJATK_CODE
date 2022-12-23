package zad1;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;


class GUI extends JFrame {

    private static String[] langs = {"pl", "en"};

    private List<Info> info;

    private Panel panel;

    GUI(List<Info> info) {
        this.info = info;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setTitle("Travel Data");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(screenSize.width/3, screenSize.height/3));
        setLocation(screenSize.width/4, screenSize.height/4);

        panel = new Panel(this);

        setContentPane(panel);

        pack();
        setVisible(true);
    }

    public static String[] getLangs() {
        return langs;
    }

    private String lang;

    public void setLang(String actionCommand) {
        lang = actionCommand;

        String[] columns;

        if(Objects.equals(lang, "en")) {
            columns = new String[]{
                    "countryCode",
                    "country",
                    "startDate",
                    "endDate",
                    "location",
                    "price",
                    "currency"
            };
        }else {
            columns = new String[]{
                    "kodKraju",
                    "kraj",
                    "dataWyjzadu",
                    "dataPowrotu",
                    "miejsce",
                    "cena",
                    "waluta"
            };
        }

        Object[][] info = new Object[getInfo().size()][];
        for (int i = 0; i < getInfo().size(); i++) {
            Info tmp = getInfo().get(i);
            if(Objects.equals(lang, "en") && Objects.equals(tmp.getMiejsce(), "jezioro")){
                tmp.setMiejsce("lake");
            }
            if(Objects.equals(lang, "en") && Objects.equals(tmp.getMiejsce(), "góry")){
                tmp.setMiejsce("mountains");
            }
            if(Objects.equals(lang, "en") && Objects.equals(tmp.getMiejsce(), "morze")){
                tmp.setMiejsce("sea");
            }
            if(Objects.equals(lang, "en") && Objects.equals(tmp.getKraj(), "Włochy")){
                tmp.setKraj("Italy");
            }
            if(Objects.equals(lang, "en") && Objects.equals(tmp.getKraj(), "Japonia")){
                tmp.setKraj("Japan");
            }
            if(Objects.equals(lang, "pl") && Objects.equals(tmp.getKraj(), "United States")){
                tmp.setKraj("Stany Zjednoczone Ameryki");
            }
            if(Objects.equals(lang, "pl") && Objects.equals(tmp.getMiejsce(), "lake")){
                tmp.setMiejsce("jezioro");
            }
            if(Objects.equals(lang, "pl") && Objects.equals(tmp.getMiejsce(), "sea")){
                tmp.setMiejsce("morze");
            }
            if(Objects.equals(lang, "pl") && Objects.equals(tmp.getMiejsce(), "mountains")){
                tmp.setMiejsce("góry");
            }
            info[i] = tmp.toArray();
        }

        JTable table = new JTable(info, columns);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        table.setPreferredSize(new Dimension(screenSize.width, screenSize.height));
        table.setLocation(screenSize.width, screenSize.height);

        remove(panel);
        add(new JScrollPane(table));

        revalidate();
        repaint();
    }

    public List<Info> getInfo() {
        return info;
    }
}