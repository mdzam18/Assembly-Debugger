package DAP;

public interface Request extends ProtocolMessage{
    Type type = Type.request;

    /**
     * The command to execute.
     */
    String command = "";

    /**
     * Object containing arguments for the command.
     */
    Object arguments = null;
}
