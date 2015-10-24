package cz.nitramek.networking;

public abstract class AbstractListener implements Runnable {

    boolean running;

    public final void run() {
        while (isRunning()) {
            doStuff();
        }
    }

    public abstract void doStuff();

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }


}
