package src.DebugAdapter;

import com.google.gson.Gson;
import src.DapClasses.Response;
import src.DapClasses.Sources.Source;
import src.DapClasses.StackFrames.StackFrame;
import src.DapClasses.StackFrames.StackTraceRequest;
import src.DapClasses.StackFrames.StackTraceResponse;
import src.Emulator.AssemblyEmulator.AssemblyEmulator;

import java.util.ArrayList;
import java.util.List;

public class StackTraceManager {
    private SendProtocolMessage send;
    private CallEmulatorMethods emulatorMethods;

    public StackTraceManager(){
        send = new SendProtocolMessage();
        emulatorMethods = new CallEmulatorMethods();
    }
    private String processStackTraceRequest(String name, String program, List<Integer> breakpointLineNumbers, AssemblyEmulator emulator, Gson gson, String json) {
        StackTraceRequest request = gson.fromJson(json, StackTraceRequest.class);
        Response r = new Response();
        r.setSuccess(true);
        r.setRequest_seq(request.getSeq());
        r.setCommand("stackTrace");
        StackTraceResponse response = new StackTraceResponse();
        ArrayList<String> callstack = emulator.getCallStack();
        StackFrame[] stackFrames = new StackFrame[callstack.size()];
        for (int i = 0; i < callstack.size(); i++) {
            stackFrames[i] = new StackFrame();
            stackFrames[i].setColumn(0);
            stackFrames[i].setId(0);
            if (emulator.getCurrentLine() == 0) {
                stackFrames[i].setLine(breakpointLineNumbers.get(0));
            } else {
                stackFrames[i].setLine(emulator.getCurrentLine() + 1);
            }
            stackFrames[i].setName(callstack.get(i));
            stackFrames[i].setSource(createSource(name, program));
        }
        response.setStackFrames(stackFrames);
        response.setTotalFrames(response.getStackFrames().length);
        r.setBody(response);
        String jsonResponse = gson.toJson(r);
        return jsonResponse;
    }

    private Source createSource(String name, String program) {
        Source source = new Source();
        source.setName(name);
        source.setAdapterData("mock-adapter-data");
        source.setSourceReference(0);
        source.setPath(program);
        return source;
    }

    public String createStackTraceResponse(String name, String program, List<Integer> breakpoints, AssemblyEmulator emulator, Gson gson, String json) {
        try {
            String StackTraceRes = processStackTraceRequest(name, program, breakpoints, emulator, gson, json);
            if (emulator.getActualLineNumber() == 0) {
                emulatorMethods.callEmulatorNextUntilFistBreakPoint(emulator, breakpoints, gson);
            }
            send.sendProtocolMessage(StackTraceRes);
            return StackTraceRes;
        } catch (Exception exception) {
            //  processEmulatorException(exception);
        }
        return "";
    }
}
