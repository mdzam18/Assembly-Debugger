package src.DapClasses;

public class StackTraceRequest extends Request{
    public StackTraceRequest() {
        super.setCommand("stackTrace");
    }
    private StackTraceArguments arguments;
}
