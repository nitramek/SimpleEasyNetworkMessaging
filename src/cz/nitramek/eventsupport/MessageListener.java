package cz.nitramek.eventsupport;

import java.util.EventListener;


public interface MessageListener extends EventListener {
    void onMessageReceived(EventMessage message);
}
