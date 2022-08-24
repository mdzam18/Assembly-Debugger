package src.DapClasses;

public class SetBreakpointsResponse extends Response {
    public SetBreakpointsResponse(){
        super.setCommand("setBreakpoints");
    }
   // private Breakpoint[] breakpoints;
//    public SetBreakpointsResponse(){
//        Breakpoint[] breakpoints = new Breakpoint[5];
//        super.setBody(breakpoints);
//    }
}
