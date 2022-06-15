package DapClasses;

public class InitializeRequest extends Request {
    private String command = "initialize";
    private InitializeRequestArguments arguments;

    @Override
    public String getCommand() {
        return command;
    }

    @Override
    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public InitializeRequestArguments getArguments() {
        return arguments;
    }

    public void setArguments(InitializeRequestArguments arguments) {
        this.arguments = arguments;
    }
}
