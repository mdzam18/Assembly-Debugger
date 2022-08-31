package src.DapClasses.SetBreakpoints;

import src.DapClasses.Request;

public class SetExceptionBreakpointsRequest extends Request {
    public SetExceptionBreakpointsRequest(){
        super.setCommand("setExceptionBreakpoints");
    }
    private SetExceptionBreakpointsArguments arguments;

    public SetExceptionBreakpointsArguments getArguments() {
        return arguments;
    }

    public void setArguments(SetExceptionBreakpointsArguments arguments) {
        this.arguments = arguments;
    }
}