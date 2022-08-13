package src.DapClasses;

public class SetBreakpointsRequest extends Request{
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
