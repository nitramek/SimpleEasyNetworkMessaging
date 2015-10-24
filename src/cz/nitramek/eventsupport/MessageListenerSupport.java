package cz.nitramek.eventsupport;

import java.util.ArrayList;
import java.util.List;

public class MessageListenerSupport {
    private List<MessageListener> messageListeners;

    public MessageListenerSupport() {
        messageListeners = new ArrayList<>();
    }

    public void addMessageListener(MessageListener listener) {
        messageListeners.add(listener);

    }

    public void removeMessageListener(MessageListener listener) {
        messageListeners.remove(listener);
    }

    public void fireMessageEvent(Object source, String message) {
        EventMessage eventMessage = new EventMessage(source, message);
        for (MessageListener listener : messageListeners) {
            listener.onMessageReceived(eventMessage);
        }
    }
}
