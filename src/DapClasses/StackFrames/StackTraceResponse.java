package src.DapClasses.StackFrames;

public class StackTraceResponse {
//    public StackTraceResponse(){
//        super.setCommand("stackTrace");
//    }
    private StackFrame[] stackFrames;
    private int totalFrames;
    //private Object body;

    public StackFrame[] getStackFrames() {
        return stackFrames;
    }

    public void setStackFrames(StackFrame[] stackFrames) {
        this.stackFrames = stackFrames;
    }

    public int getTotalFrames() {
        return totalFrames;
    }

    public void setTotalFrames(int totalFrames) {
        this.totalFrames = totalFrames;
    }

//    @Override
//    public Object getBody() {
//        return body;
//    }
//
//    @Override
//    public void setBody(Object body) {
//        this.body = body;
//    }
}
