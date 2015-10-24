package cz.nitramek.networking;


public abstract class ServiceThread extends Thread {

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    private boolean running;

    @Override
    public void run() {
        while (isRunning()) {
            doStuff();
        }
    }

    @Override
    public synchronized void start() {
        try {
            super.start();
        } catch (IllegalThreadStateException e) {
            System.out.print("Thread Already started");
            e.printStackTrace();
        }
    }


    protected abstract void doStuff();
}
