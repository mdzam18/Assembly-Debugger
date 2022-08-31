package src.DapClasses.SetBreakpoints;

import src.DapClasses.FunctionBreakpoint;

public class SetFunctionBreakpointsArguments {
    private FunctionBreakpoint[] breakpoints;

    public FunctionBreakpoint[] getBreakpoints() {
        return breakpoints;
    }

    public void setBreakpoints(FunctionBreakpoint[] breakpoints) {
        this.breakpoints = breakpoints;
    }
}
