package src.DapClasses.Launches;

import src.DapClasses.BaseClasses.Request;

public class LaunchRequest extends Request {
    public LaunchRequest() {
        super.setCommand("launch");
    }
    private LaunchRequestArguments arguments;

    public LaunchRequestArguments getArguments() {
        return arguments;
    }

    public void setArguments(LaunchRequestArguments arguments) {
        this.arguments = arguments;
    }
}
