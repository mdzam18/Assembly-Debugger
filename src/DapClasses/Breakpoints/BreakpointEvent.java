package src.DapClasses.Breakpoints;

public class BreakpointEvent {
    private String reason;
    private Breakpoint breakpoint;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Breakpoint getBreakpoint() {
        return breakpoint;
    }

    public void setBreakpoint(Breakpoint breakpoint) {
        this.breakpoint = breakpoint;
    }
}
