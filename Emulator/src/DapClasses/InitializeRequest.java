package DapClasses;
public class InitializeRequest extends Request{
    public InitializeRequest() {
        super.setCommand("initialize");
    }

    private InitializeRequestArguments arguments;

    public InitializeRequestArguments getArguments() {
        return arguments;
    }

    public void setArguments(InitializeRequestArguments arguments) {
        this.arguments = arguments;
    }
}
