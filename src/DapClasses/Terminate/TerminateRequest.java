package src.DapClasses.Terminate;

import src.DapClasses.Request;

public class TerminateRequest extends Request {
    public TerminateRequest(){
        super.setCommand("terminate");
    }
    private TerminateArguments arguments;

    public TerminateArguments getArguments() {
        return arguments;
    }

    public void setArguments(TerminateArguments arguments) {
        this.arguments = arguments;
    }
}
