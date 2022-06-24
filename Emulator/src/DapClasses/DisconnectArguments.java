package DapClasses;

public class DisconnectArguments {
    private boolean restart;
    private boolean terminateDebuggee;

    public boolean isRestart() {
        return restart;
    }

    public void setRestart(boolean restart) {
        this.restart = restart;
    }

    public boolean isTerminateDebuggee() {
        return terminateDebuggee;
    }

    public void setTerminateDebuggee(boolean terminateDebuggee) {
        this.terminateDebuggee = terminateDebuggee;
    }

    public boolean isSuspendDebuggee() {
        return suspendDebuggee;
    }

    public void setSuspendDebuggee(boolean suspendDebuggee) {
        this.suspendDebuggee = suspendDebuggee;
    }

    private boolean suspendDebuggee;
}
