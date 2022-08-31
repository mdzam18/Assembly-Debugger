package src.DapClasses.StepOut;

import src.DapClasses.Request;

public class StepOutRequest extends Request {
    public StepOutRequest() {
        super.setCommand("stepOut");
    }
    private StepOutArguments arguments;

    public StepOutArguments getArguments() {
        return arguments;
    }

    public void setArguments(StepOutArguments arguments) {
        this.arguments = arguments;
    }
}
