package zad1;

public class StringTask implements Runnable {

    private TaskState state;
    private String text;
    private int times;
    private String out;
    private Thread thread;

    public StringTask(String text, int times) {
        this.times = times;
        this.text = text;
        this.state = TaskState.CREATED;
        this.out = "";
    }

    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    public void abort() {
        this.state = TaskState.ABORTED;
        thread.interrupt();
    }

    public String getResult() {
        return out;
    }

    public TaskState getState() {
        return state;
    }

    public boolean isDone() {
        return state.equals(TaskState.READY) || state.equals(TaskState.ABORTED);
    }

    @Override
    public void run() {
        this.state = TaskState.RUNNING;
        for (int i = 0; i < this.times && (!thread.isInterrupted()); i++) {
            out = out + text;
        }
        if(this.state == TaskState.RUNNING) {
            this.state = TaskState.READY;
        }
    }
}