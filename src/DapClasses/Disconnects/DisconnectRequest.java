package src.DapClasses.Disconnects;

import src.DapClasses.Request;

public class DisconnectRequest extends Request {
    public DisconnectRequest() {
        super.setCommand("disconnect");
    }
    private DisconnectArguments arguments;

    public DisconnectArguments getArguments() {
        return arguments;
    }

    public void setArguments(DisconnectArguments arguments) {
        this.arguments = arguments;
    }
}
