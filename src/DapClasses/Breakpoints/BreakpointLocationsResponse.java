package src.DapClasses.Breakpoints;

import src.DapClasses.Response;

public class BreakpointLocationsResponse extends Response {
    public BreakpointLocationsResponse(){
        super.setCommand("breakpointLocations");
    }

}
