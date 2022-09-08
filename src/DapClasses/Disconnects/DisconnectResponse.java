package src.DapClasses.Disconnects;

import src.DapClasses.BaseClasses.Response;

public class DisconnectResponse extends Response {
    public DisconnectResponse(){
        super.setCommand("disconnect");
    }
}
