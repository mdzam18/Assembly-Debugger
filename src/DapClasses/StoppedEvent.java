package src.DapClasses;

public class StoppedEvent extends Event {
    public StoppedEvent(){
        super.setEvent("event");
    }
    private String reason;
    private String description;
    private int threadId;
    private boolean preserveFocusHint;
    private String text;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public boolean isPreserveFocusHint() {
        return preserveFocusHint;
    }

    public void setPreserveFocusHint(boolean preserveFocusHint) {
        this.preserveFocusHint = preserveFocusHint;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isAllThreadsStopped() {
        return allThreadsStopped;
    }

    public void setAllThreadsStopped(boolean allThreadsStopped) {
        this.allThreadsStopped = allThreadsStopped;
    }

    public int[] getHitBreakpointIds() {
        return hitBreakpointIds;
    }

    public void setHitBreakpointIds(int[] hitBreakpointIds) {
        this.hitBreakpointIds = hitBreakpointIds;
    }

    private boolean allThreadsStopped;
    private int[] hitBreakpointIds;

}
