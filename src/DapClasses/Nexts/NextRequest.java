package src.DapClasses.Nexts;

import src.DapClasses.BaseClasses.Request;

public class NextRequest extends Request {
    public NextRequest(){
        super.setCommand("next");
    }
    //private String command = "next";


//    public String getCommand() {
//        return command;
//    }
//
//
//    public void setCommand(String command) {
//        this.command = command;
//    }


    public NextArguments getArguments() {
        return arguments;
    }

    public void setArguments(NextArguments arguments) {
        this.arguments = arguments;
    }

    private NextArguments arguments;
}
