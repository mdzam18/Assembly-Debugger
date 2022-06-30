package src.DapClasses;

public class ContinueResponse extends Response {
    private boolean allThreadsContinued;

    public boolean isAllThreadsContinued() {
        return allThreadsContinued;
    }

    public void setAllThreadsContinued(boolean allThreadsContinued) {
        this.allThreadsContinued = allThreadsContinued;
    }
}
