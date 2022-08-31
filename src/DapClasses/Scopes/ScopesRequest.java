package src.DapClasses.Scopes;

import src.DapClasses.Request;

public class ScopesRequest extends Request {
    public ScopesRequest() {
        super.setCommand("scopes");
    }
    private ScopesArguments arguments;

    public ScopesArguments getArguments() {
        return arguments;
    }

    public void setArguments(ScopesArguments arguments) {
        this.arguments = arguments;
    }
}
