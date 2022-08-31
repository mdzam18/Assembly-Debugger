package src.DapClasses.SetBreakpoints;

import src.DapClasses.Breakpoints.Breakpoint;
import src.DapClasses.Response;

public class SetExceptionBreakpointsResponse extends Response {
    public SetExceptionBreakpointsResponse() {
        Breakpoint[] breakpoints = new Breakpoint[5];
        super.setBody(breakpoints);
    }

}
