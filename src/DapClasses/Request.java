package src.DapClasses;

public class Request extends ProtocolMessage {
    public Request(){
        super.setType("request");
    }
    private String command;
    //private Object arguments;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }


}
