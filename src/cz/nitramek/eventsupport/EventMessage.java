package cz.nitramek.eventsupport;

import java.util.EventObject;


public class EventMessage extends EventObject {
    String message;

    public EventMessage(Object source, String message) {
        super(source);
    }

    public String getMessage() {
        return message;
    }
}
