package src.DapClasses.Configurations;

import src.DapClasses.BaseClasses.Response;

public class ConfigurationDoneResponse extends Response {
    public ConfigurationDoneResponse(){
        super.setCommand("configurationDone");
    }
}
