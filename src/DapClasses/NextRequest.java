package src.DapClasses;

public class NextRequest extends Request {
    private String command = "next";


    public String getCommand() {
        return command;
    }


    public void setCommand(String command) {
        this.command = command;
    }


    public NextArguments getArguments() {
        return arguments;
    }

    public void setArguments(NextArguments arguments) {
        this.arguments = arguments;
    }

    private NextArguments arguments;
}
