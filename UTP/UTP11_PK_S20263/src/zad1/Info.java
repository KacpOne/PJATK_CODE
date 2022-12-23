package zad1;

import java.util.Date;
import java.util.Locale;

public class Info {
    private Locale lokalizacja;
    private String kraj;
    private Date dataWyjazdu;
    private Date dataPowrotu;
    private String miejsce;
    private Double cena;
    private String symbol_waluty;

    public Info(Locale lokalizacja, String kraj, Date dataWyjazdu, Date dataPowrotu, String miejsce, Double cena,
                String symbol_waluty){
        this.lokalizacja=lokalizacja;
        this.kraj = kraj;
        this.dataPowrotu = dataPowrotu;
        this.dataWyjazdu = dataWyjazdu;
        this.miejsce = miejsce;
        this.cena = cena;
        this.symbol_waluty = symbol_waluty;
    }

    public String getKraj() {
        return kraj;
    }

    public Date getDataWyjazdu() {
        return dataWyjazdu;
    }

    public Date getDataPowrotu() {
        return dataPowrotu;
    }

    public String getMiejsce() {
        return miejsce;
    }

    public Double getCena() {
        return cena;
    }

    public String getSymbol_waluty() {
        return symbol_waluty;
    }

    public void setKraj(String kraj) {
        this.kraj = kraj;
    }

    public void setMiejsce(String miejsce) {
        this.miejsce = miejsce;
    }

    Object[] toArray() {
        return new Object[]{lokalizacja, kraj, dataWyjazdu, dataPowrotu, miejsce, cena, symbol_waluty};
    }
}
