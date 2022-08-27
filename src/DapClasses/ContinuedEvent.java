package src.DapClasses;

public class ContinuedEvent {
    private int threadId;
    private boolean allThreadsContinued;

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public boolean isAllThreadsContinued() {
        return allThreadsContinued;
    }

    public void setAllThreadsContinued(boolean allThreadsContinued) {
        this.allThreadsContinued = allThreadsContinued;
    }
}
