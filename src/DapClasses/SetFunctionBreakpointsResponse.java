package src.DapClasses;

import src.DapClasses.Breakpoints.Breakpoint;

public class SetFunctionBreakpointsResponse extends Response {
    private Breakpoint[] breakpoints;
    public SetFunctionBreakpointsResponse(){
        super.setCommand("setFunctionBreakpoints");
    }
    private Object body;
    //public InitializeResponse() {
    //super.setCommand("initialize");
    //}

    public Breakpoint[] getBreakpoints() {
        return breakpoints;
    }

    public void setBreakpoints(Breakpoint[] breakpoints) {
        this.breakpoints = breakpoints;
    }

    @Override
    public Object getBody() {
        return body;
    }

    @Override
    public void setBody(Object body) {
        this.body = body;
    }
}
