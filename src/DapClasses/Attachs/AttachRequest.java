package src.DapClasses.Attachs;

import src.DapClasses.Request;

public class AttachRequest extends Request {
    public AttachRequest() {
        super.setCommand("attach");
    }
    private AttachRequestArguments arguments;

    public AttachRequestArguments getArguments() {
        return arguments;
    }

    public void setArguments(AttachRequestArguments arguments) {
        this.arguments = arguments;
    }
}
