package src.DapClasses.Disconnects;

import src.DapClasses.Response;

public class DisconnectResponse extends Response {
    public DisconnectResponse(){
        super.setCommand("disconnect");
    }
}
