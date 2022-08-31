package src.DapClasses.SetVariables;

import src.DapClasses.Request;

public class SetVariableRequest extends Request {
    public SetVariableRequest(){
        super.setCommand("setVariable");
    }
    private SetVariableArguments arguments;

    public SetVariableArguments getArguments() {
        return arguments;
    }

    public void setArguments(SetVariableArguments arguments) {
        this.arguments = arguments;
    }
}
