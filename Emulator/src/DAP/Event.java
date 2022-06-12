package DAP;

public interface Event extends ProtocolMessage{
    Type type = Type.event;

    /**
     * Type of event.
     */
    String event = "";

    /**
     * Event-specific information.
     */
    Object body = null;
}
