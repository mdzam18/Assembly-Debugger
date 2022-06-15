package DapClasses;

public class NextRequest extends Request {
    private String command = "next";

    @Override
    public String getCommand() {
        return command;
    }

    @Override
    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public NextArguments getArguments() {
        return arguments;
    }

    public void setArguments(NextArguments arguments) {
        this.arguments = arguments;
    }

    private NextArguments arguments;
}
