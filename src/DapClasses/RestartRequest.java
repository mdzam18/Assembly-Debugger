package src.DapClasses;

public class RestartRequest extends Request{
    public RestartRequest(){
        super.setCommand("restart");
    }
    private RestartArguments arguments;

    public RestartArguments getArguments() {
        return arguments;
    }

    public void setArguments(RestartArguments arguments) {
        this.arguments = arguments;
    }
}
