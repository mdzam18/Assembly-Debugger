package src.DapClasses.StackFrames;

import src.DapClasses.BaseClasses.Request;

public class StackTraceRequest extends Request {
    public StackTraceRequest() {
        super.setCommand("stackTrace");
    }
    private StackTraceArguments arguments;
}
