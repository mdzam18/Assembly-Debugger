package src.DapClasses;

import src.DapClasses.Event.Event;

public class ThreadEvent extends Event {
    public ThreadEvent() {
        super.setEvent("thread");
    }
    private String reason;
    private int threadId;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }
}
