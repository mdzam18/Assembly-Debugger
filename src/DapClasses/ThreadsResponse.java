package src.DapClasses;

public class ThreadsResponse extends Response{
    private java.lang.Thread[] threads;
    private Object body;

    public java.lang.Thread[] getThreads() {
        return threads;
    }

    public void setThreads(java.lang.Thread[] threads) {
        this.threads = threads;
    }

    @Override
    public Object getBody() {
        return body;
    }

    @Override
    public void setBody(Object body) {
        this.body = body;
    }
}
