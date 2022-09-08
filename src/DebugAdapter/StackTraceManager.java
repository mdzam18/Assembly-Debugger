package src.DebugAdapter;

import com.google.gson.Gson;
import src.DapClasses.BaseClasses.Response;
import src.DapClasses.Sources.Source;
import src.DapClasses.StackFrames.StackFrame;
import src.DapClasses.StackFrames.StackTraceRequest;
import src.DapClasses.StackFrames.StackTraceResponse;
import src.Emulator.AssemblyEmulator.AssemblyEmulator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StackTraceManager {
    private CallEmulatorMethods emulatorMethods;


    public StackTraceManager() throws IOException {
        emulatorMethods = new CallEmulatorMethods();
    }

    //Shows stack frame of every method in call stack
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
            if (emulator.getActualLineNumber() == 0) {
                stackFrames[i].setLine(emulator.getActualLineNumber() + 1);
            } else {
                stackFrames[i].setLine(emulator.getActualLineNumber() + 1);
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

    public String createStackTraceResponse(SendProtocolMessage send, ExceptionInfoManager exceptionInfoManager, String name, String program, List<Integer> breakpoints, AssemblyEmulator emulator, Gson gson, String json) throws IOException {
        try {
            if (emulator.getActualLineNumber() == 0) {
                emulatorMethods.callEmulatorNextUntilFistBreakPoint(send, exceptionInfoManager, emulator, breakpoints, gson);
            }
            String StackTraceRes = processStackTraceRequest(name, program, breakpoints, emulator, gson, json);
            if (emulator.getActualLineNumber() == 0) {


               // emulatorMethods.callEmulatorNextUntilFistBreakPoint(send, exceptionInfoManager, emulator, breakpoints, gson);
            }
            send.sendProtocolMessage(StackTraceRes);
            return StackTraceRes;
        } catch (Exception exception) {

            //  processEmulatorException(exception);
        }
        return "";
    }
}
