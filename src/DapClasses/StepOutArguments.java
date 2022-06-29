package src.DapClasses;

public class StepOutArguments {
    private int threadId;
    private boolean singleThread;
    private String granularity;

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

    public String getGranularity() {
        return granularity;
    }

    public void setGranularity(String granularity) {
        this.granularity = granularity;
    }
}
