package DapClasses;

public class RunInTerminalResponse extends Response{
    private int processId;
    private int shellProcessId;

    public int getProcessId() {
        return processId;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    public int getShellProcessId() {
        return shellProcessId;
    }

    public void setShellProcessId(int shellProcessId) {
        this.shellProcessId = shellProcessId;
    }

    @Override
    public Object getBody() {
        return body;
    }

    @Override
    public void setBody(Object body) {
        this.body = body;
    }

    private Object body;
}
