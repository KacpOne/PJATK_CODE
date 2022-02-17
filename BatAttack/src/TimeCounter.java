package batattack;

public class TimeCounter {

    private long start;
    private long stop;

    public TimeCounter() {
        this.start = 0;
        this.stop = 0;
    }

    public void start() {
        this.start = System.currentTimeMillis();
    }

    public void stop() {
        this.stop = System.currentTimeMillis();
    }

    public String getTime() {
        return String.valueOf(System.currentTimeMillis() - this.start);
    }

    public double getElapsedTimeSecs() {
        double elapsed;
        elapsed = ((double) (stop - start)) / 1000;
        return elapsed;
    }
}
