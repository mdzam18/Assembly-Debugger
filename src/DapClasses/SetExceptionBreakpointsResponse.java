package src.DapClasses;
public class SetExceptionBreakpointsResponse extends Response{
    public SetExceptionBreakpointsResponse() {
        Breakpoint[] breakpoints = new Breakpoint[5];
        super.setBody(breakpoints);
    }

}
