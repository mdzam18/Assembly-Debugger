package src.DebugAdapter;

import src.DapClasses.Event.Event;
import src.Emulator.AssemblyEmulator.AssemblyEmulator;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CallEmulatorMethods {
    private OutputsManager outputsManager;


    public CallEmulatorMethods() throws IOException {
        outputsManager = new OutputsManager();
    }

    //Calls next method until reaches first breakpoint
    public void callEmulatorNextUntilFistBreakPoint(SendProtocolMessage send, ExceptionInfoManager exceptionInfoManager, AssemblyEmulator emulator, List<Integer> breakpointLineNumbers, Gson gson) throws Exception {
        try {
            boolean first = true;
            while (true) {
                if (emulator.getActualLineNumber() == 0) {
                    first = false;
                }
                if (!first && breakpointLineNumbers.contains(emulator.getActualLineNumber() + 1)) {

                    break;
                }
                first = false;
                boolean isProgramRunning = emulator.next();
                if (emulator.getAssertsText().length() != 0) {
                    //print asserts text
                    outputsManager.showTextInConsole(emulator.getAssertsText(), emulator, gson, send);
                }
                if (!isProgramRunning) {
                    if (emulator.containsRv()) {
                        outputsManager.showTextInConsole("RV: " + emulator.getRv() + "\n", emulator, gson, send);
                    }
                    Event terminatedEvent = new Event();
                    terminatedEvent.setEvent("terminated");
                    send.sendProtocolMessage(gson.toJson(terminatedEvent));
                    break;
                }
            }
        } catch (Exception e) {

            exceptionInfoManager.processEmulatorException(e, gson, send);
        }
    }

    //Calls next for n times
    public void callEmulatorNextNTimes(SendProtocolMessage send, int num, AssemblyEmulator emulator, Gson gson, ExceptionInfoManager exceptionInfoManager) throws Exception {
        for (int i = 0; i < num; i++) {
            try {
                boolean isProgramRunning = emulator.next();
                if (emulator.getAssertsText().length() != 0) {
                    //print asserts text
                    outputsManager.showTextInConsole(emulator.getAssertsText(), emulator, gson, send);
                }
                if (!isProgramRunning) {
                    if (emulator.containsRv()) {
                        outputsManager.showTextInConsole("RV: " + emulator.getRv() + "\n", emulator, gson, send);
                    }
                    Event terminatedEvent = new Event();
                    terminatedEvent.setEvent("terminated");
                    send.sendProtocolMessage(gson.toJson(terminatedEvent));
                    break;
                }
            } catch (Exception e) {

                exceptionInfoManager.processEmulatorException(e, gson, send);
            }
        }
    }
}
