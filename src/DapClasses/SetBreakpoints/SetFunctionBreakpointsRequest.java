package src.DapClasses.SetBreakpoints;

import src.DapClasses.Request;

public class SetFunctionBreakpointsRequest extends Request {
    public SetFunctionBreakpointsRequest() {
        super.setCommand("setFunctionBreakpoints");
    }
    private SetFunctionBreakpointsArguments arguments;

    public SetFunctionBreakpointsArguments getArguments() {
        return arguments;
    }

    public void setArguments(SetFunctionBreakpointsArguments arguments) {
        this.arguments = arguments;
    }
}
