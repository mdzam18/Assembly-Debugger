package src.DapClasses.SetBreakpoints;

import src.DapClasses.Request;

public class SetBreakpointsRequest extends Request {
    public SetBreakpointsRequest() {
        super.setCommand("setBreakpoints");
    }

    private SetBreakpointsArguments arguments;

    public SetBreakpointsArguments getArguments() {
        return arguments;
    }

    public void setArguments(SetBreakpointsArguments arguments) {
        this.arguments = arguments;
    }
}
