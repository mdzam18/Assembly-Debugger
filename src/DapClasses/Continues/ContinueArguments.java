package src.DapClasses.Continues;

public class ContinueArguments {
    private int threadId;

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public boolean isSingleThread() {
        return singleThread;
    }

    public void setSingleThread(boolean singleThread) {
        this.singleThread = singleThread;
    }

    private boolean singleThread;
}
