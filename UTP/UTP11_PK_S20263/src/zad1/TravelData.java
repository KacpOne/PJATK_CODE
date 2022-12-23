package zad1;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TravelData {

    private Scanner scanner;
    private ArrayList<Info> info = new ArrayList<>();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public TravelData(File dataDir) {
        try {
            File[] tmp = dataDir.listFiles();
            for(int i = 0; i < Objects.requireNonNull(tmp).length; i++) {
                scanner = new Scanner(tmp[i]);
                while (scanner.hasNextLine()) {
                    String[] tmpString = scanner.nextLine().split("\t");
                    Locale tmplok = Locale.forLanguageTag(tmpString[0].replace("_", "-"));
                    Date tmpdate1 = simpleDateFormat.parse(tmpString[2]);
                    Date tmpdate2 = simpleDateFormat.parse(tmpString[3]);
                    if (Objects.equals(tmpString[6], "PLN")) {
                        tmpString[5] = tmpString[5].replace(",", ".");
                    } else {
                        tmpString[5] = tmpString[5].replace(",", "");
                    }
                    double tmpcena = Double.valueOf(tmpString[5]);
                    this.info.add(new Info(tmplok, tmpString[1], tmpdate1, tmpdate2, tmpString[4], tmpcena, tmpString[6]));
                }
            }
            scanner.close();
        } catch (FileNotFoundException | ParseException e) {
            e.printStackTrace();
        }
    }

    public List<String> getOffersDescriptionsList(String locale, String dateFormat) {

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        ArrayList<String> tmpList = new ArrayList<>();
        String tmp = "";
        for (Info value : info) {
            if(locale.equals("en_GB")) {
                if(Objects.equals(value.getKraj(), "Japonia")){
                    tmp+= "Japan ";
                }
                else if(Objects.equals(value.getKraj(), "Włochy")){
                    tmp+= "Italy ";
                }
                else{
                    tmp+= "United States ";
                }
            }
            else if(locale.equals("pl")){
                if(Objects.equals(value.getKraj(), "United States")){
                    tmp+= "Stany Zjednoczone Ameryki ";
                }
                else{
                    tmp+= value.getKraj() + " ";
                }
            }
            else if(locale.equals("pl_PL")) {
                if (Objects.equals(value.getKraj(), "United States")) {
                    tmp += "Stany Zjednoczone Ameryki ";
                }
                else {
                    tmp+= value.getKraj() + " ";
                }
            }
            tmp += sdf.format(value.getDataWyjazdu()) + " " + sdf.format(value.getDataPowrotu()) + " ";
            if(locale.equals("en_GB")){
                if(Objects.equals(value.getMiejsce(), "jezioro")){
                    tmp += "lake ";
                }
                else if(Objects.equals(value.getMiejsce(), "góry")){
                    tmp+= "mountains ";
                }
                else if(Objects.equals(value.getMiejsce(), "morze")){
                    tmp+= "sea ";
                }
            }
            else{
                if(Objects.equals(value.getMiejsce(), "jezioro")){
                    tmp+= "jezioro ";
                }
                else if(Objects.equals(value.getMiejsce(), "morze")){
                    tmp+= "morze ";
                }
                else if(Objects.equals(value.getMiejsce(), "mountains")){
                    tmp+= "góry ";
                }
            }
            tmp+= value.getCena() + " ";
            tmp+= value.getSymbol_waluty();
            tmpList.add(tmp);
            tmp = "";
        }
        return tmpList;
    }

    public ArrayList<Info> getInfo() {
        return info;
    }
}
