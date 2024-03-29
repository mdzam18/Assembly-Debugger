package src.DapClasses.Continues;

import src.DapClasses.BaseClasses.Request;

public class ContinueRequest extends Request {
    public ContinueRequest() {
        super.setCommand("continue");
    }
    private ContinueArguments arguments;

    public ContinueArguments getArguments() {
        return arguments;
    }

    public void setArguments(ContinueArguments arguments) {
        this.arguments = arguments;
    }
}
