package src.DebugAdapter;

import com.google.gson.Gson;
import src.DapClasses.Event.Event;
import src.DapClasses.Output.OutputEvent;
import src.Emulator.AssemblyEmulator.AssemblyEmulator;

import java.io.IOException;

public class OutputsManager {
    private SendProtocolMessage send;

    public OutputsManager(){
        send = new SendProtocolMessage();
    }

    public void showTextInConsole(String text, AssemblyEmulator emulator, Gson gson) throws IOException {
        OutputEvent oEvent = new OutputEvent();
        oEvent.setVariablesReference(0);
        oEvent.setOutput(text);
        oEvent.setLine(emulator.getActualLineNumber());
        oEvent.setCategory("stdout");
        oEvent.setGroup("end");
        Event e1 = new Event();
        e1.setEvent("output");
        e1.setBody(oEvent);
        send.sendProtocolMessage(gson.toJson(e1));
    }
}
