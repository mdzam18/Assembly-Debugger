package src.DapClasses.Initialize;

import src.DapClasses.Capabilities.Capabilities;

public class InitializeResponse  {
    //public InitializeResponse() {
        //super.setCommand("initialize");
    //}
    private Capabilities body;

    public Capabilities getBody() {
        return body;
    }

    public void setBody(Capabilities body) {
        this.body = body;
    }
}
