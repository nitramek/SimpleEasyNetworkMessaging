package cz.nitramek.networking;

import cz.nitramek.eventsupport.MessageListener;

/**
 * Created by Martin on 24.10.2015.
 */
public interface Listener {

    void startListening();

    void stopListening();

    boolean isRunning();

    void addMessageListener(MessageListener listener);

    void removeMessageListener(MessageListener listener);

    void setPort(int port);
}
