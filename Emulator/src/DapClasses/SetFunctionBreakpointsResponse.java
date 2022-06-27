package DapClasses;

public class SetFunctionBreakpointsResponse extends Response{
    private Breakpoint[] breakpoints;
    private Object body;

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
