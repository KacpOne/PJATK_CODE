package zad2;


import java.util.concurrent.*;

public class Owoc implements Runnable, Future{

    private int ilosc;
    private int cena;
    private int sprzedane;
    private String nazwa;
    private Stan stan;
    private Thread thread;

    public Owoc(int ilosc, int cena, String nazwa) {

        this.ilosc = ilosc;
        this.cena = cena;
        this.nazwa = nazwa;
        this.sprzedane=0;
        this.stan = Stan.CZEKAM_NA_START;
    }

    public void sprzedaj(){
        this.sprzedane++;
        this.ilosc--;
    }
    public void zacznij(){
        if(this.stan != Stan.KONIEC_ZAPASOW) {
            this.stan = Stan.SPRZEDAJE;
        }
    }
    public void stop(){
        this.stan = Stan.NIE_SPRZEDAJE;
        this.cancel(true);
    }


    @Override
    public void run() {
        if(this.stan == Stan.CZEKAM_NA_START) {
            this.thread = new Thread(this);
            this.stan = Stan.SPRZEDAJE;
            thread.start();
        }
        else if(this.stan != Stan.KONIEC_ZAPASOW){
            this.zacznij();
        }
        while(!isDone() && !isCancelled()) {
            if(this.stan == Stan.SPRZEDAJE) {
                sprzedaj();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {}
            }
            else {
                try {
                    Thread.sleep(0,1);
                } catch (InterruptedException ignored) {}
            }
        }
        this.stan = Stan.KONIEC_ZAPASOW;
    }

    public int policzZarobek(){
        return (this.cena * this.sprzedane);
    }

    public String getNazwa() {
        return nazwa;
    }

    public Stan getStan() {
        return stan;
    }

    @Override
    public String toString() {
        return this.nazwa;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        if(mayInterruptIfRunning){
            Thread.currentThread().interrupt();
            return true;
        }
        return false;
    }

    @Override
    public boolean isCancelled() {
        if(Thread.currentThread().isInterrupted()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isDone() {
        if(this.ilosc!=0){
            return false;
        }
        return true;
    }

    @Override
    public Object get() {
        return this;
    }

    @Override
    public Object get(long timeout, TimeUnit unit) {
        return this;
    }
}
