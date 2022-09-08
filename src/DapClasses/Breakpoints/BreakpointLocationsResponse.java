package src.DapClasses.Breakpoints;

import src.DapClasses.BaseClasses.Response;

public class BreakpointLocationsResponse extends Response {
    public BreakpointLocationsResponse(){
        super.setCommand("breakpointLocations");
    }

}
