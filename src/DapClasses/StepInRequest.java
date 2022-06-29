package src.DapClasses;

public class StepInRequest extends Request{
    public StepInRequest() {
        super.setCommand("stepIn");
    }
    private StepInArguments arguments;

    public StepInArguments getArguments() {
        return arguments;
    }

    public void setArguments(StepInArguments arguments) {
        this.arguments = arguments;
    }
}
