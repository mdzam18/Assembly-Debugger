package src.DapClasses;
public class ConfigurationDoneRequest extends Request{
    public ConfigurationDoneRequest() {
        super.setCommand("configurationDone");
    }
    private  ConfigurationDoneArguments arguments;

    public ConfigurationDoneArguments getArguments() {
        return arguments;
    }

    public void setArguments(ConfigurationDoneArguments arguments) {
        this.arguments = arguments;
    }
}
