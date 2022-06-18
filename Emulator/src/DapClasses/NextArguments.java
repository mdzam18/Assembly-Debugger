package DapClasses;
public class NextArguments {
    private int threadId;
    private Boolean singleThread;

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public Boolean getSingleThread() {
        return singleThread;
    }

    public void setSingleThread(Boolean singleThread) {
        this.singleThread = singleThread;
    }

    public String getGranularity() {
        return granularity;
    }

    public void setGranularity(String granularity) {
        this.granularity = granularity;
    }

    private String granularity;
}
