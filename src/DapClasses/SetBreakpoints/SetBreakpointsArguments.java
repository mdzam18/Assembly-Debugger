package src.DapClasses.SetBreakpoints;

import src.DapClasses.Sources.Source;
import src.DapClasses.Sources.SourceBreakpoint;

public class SetBreakpointsArguments {
    private Source source;
    private SourceBreakpoint[] breakpoints;
    private int[] lines;

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public SourceBreakpoint[] getBreakpoints() {
        return breakpoints;
    }

    public void setBreakpoints(SourceBreakpoint[] breakpoints) {
        this.breakpoints = breakpoints;
    }

    public int[] getLines() {
        return lines;
    }

    public void setLines(int[] lines) {
        this.lines = lines;
    }

    public boolean isSourceModified() {
        return sourceModified;
    }

    public void setSourceModified(boolean sourceModified) {
        this.sourceModified = sourceModified;
    }

    private boolean sourceModified;
}
