package DapClasses;

public class PauseRequest extends Request{
    public PauseRequest() {
        super.setCommand("pause");
    }
    private PauseArguments arguments;

    public PauseArguments getArguments() {
        return arguments;
    }

    public void setArguments(PauseArguments arguments) {
        this.arguments = arguments;
    }
}
