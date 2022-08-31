package src.DapClasses.Sources;

public class SourceBreakpoint {
    private int line;
    private int column;
    private String condition;
    private String hitCondition;
    private String logMessage;

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getHitCondition() {
        return hitCondition;
    }

    public void setHitCondition(String hitCondition) {
        this.hitCondition = hitCondition;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }
}
