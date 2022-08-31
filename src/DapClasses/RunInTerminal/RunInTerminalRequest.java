package src.DapClasses.RunInTerminal;

import src.DapClasses.Request;

public class RunInTerminalRequest extends Request {
    public RunInTerminalRequest() {
        super.setCommand("runInTerminal");
    }
    private RunInTerminalRequestArguments arguments;

    public RunInTerminalRequestArguments getArguments() {
        return arguments;
    }

    public void setArguments(RunInTerminalRequestArguments arguments) {
        this.arguments = arguments;
    }
}
