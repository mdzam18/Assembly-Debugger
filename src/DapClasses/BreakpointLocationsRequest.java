package src.DapClasses;

public class BreakpointLocationsRequest extends Request{
    public BreakpointLocationsRequest(){
        super.setCommand("breakpointLocations");
    }
    private BreakpointLocationsArguments arguments;

    public BreakpointLocationsArguments getArguments() {
        return arguments;
    }

    public void setArguments(BreakpointLocationsArguments arguments) {
        this.arguments = arguments;
    }
}
