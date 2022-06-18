package DapClasses;
public class SetBreakpointsResponse extends Response{
    public SetBreakpointsResponse(){
        Breakpoint[] breakpoints = new Breakpoint[5];
        super.setBody(breakpoints);
    }
}
