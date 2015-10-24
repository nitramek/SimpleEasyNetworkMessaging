package cz.nitramek.networking;

import cz.nitramek.eventsupport.MessageListener;
import cz.nitramek.eventsupport.MessageListenerSupport;


public abstract class BasicListener implements Listener {

    private MessageListenerSupport messageListenerSupport;


    protected ServiceThread listeningThread;

    public BasicListener() {
        this.messageListenerSupport = new MessageListenerSupport();

    }

    @Override
    public void startListening() {
        if (!listeningThread.isRunning()) {
            listeningThread.setRunning(true);
            listeningThread.start();
        }

    }


    @Override
    public void stopListening() {
        try {
            listeningThread.setRunning(false);
            listeningThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isRunning() {
        return listeningThread.isRunning();
    }

    @Override
    public void addMessageListener(MessageListener listener) {
        messageListenerSupport.addMessageListener(listener);
    }

    protected void fireMessageListeners(Object source, String message) {
        messageListenerSupport.fireMessageEvent(source, message);
    }

    @Override
    public void removeMessageListener(MessageListener listener) {
        messageListenerSupport.removeMessageListener(listener);
    }

}
