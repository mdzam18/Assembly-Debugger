package DAP;

public interface ProtocolMessage {
    /**
     * Sequence number (also known as message ID). For protocol messages of type
     * 'request' this ID can be used to cancel the request.
     */
    int seq = 0;

    /**
     * Message type.
     * Values: 'request', 'response', 'event', etc.
     */
    Type type = Type.request;

}
