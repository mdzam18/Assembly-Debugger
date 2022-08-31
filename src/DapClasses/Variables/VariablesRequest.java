package src.DapClasses.Variables;

import src.DapClasses.Request;

public class VariablesRequest extends Request {
    public VariablesRequest() {
        super.setCommand("variables");
    }
    private VariablesArguments arguments;

    public VariablesArguments getArguments() {
        return arguments;
    }

    public void setArguments(VariablesArguments arguments) {
        this.arguments = arguments;
    }
}
