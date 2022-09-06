package src.DebugAdapter;

import com.google.gson.Gson;
import src.DapClasses.Breakpoints.Breakpoint;
import src.DapClasses.Continues.ContinueRequest;
import src.DapClasses.Continues.ContinueResponse;
import src.DapClasses.Event.Event;
import src.DapClasses.Response;
import src.DapClasses.StoppedEvent.StoppedEvent;
import src.Emulator.AssemblyEmulator.AssemblyEmulator;

import java.util.Arrays;
import java.util.List;

public class ContinueManager {
    private String processContinueRequest(Gson gson, String json) {
        ContinueRequest request = gson.fromJson(json, ContinueRequest.class);
        ContinueResponse response = new ContinueResponse();
        Response r = new Response();
        r.setCommand("continue");
        r.setRequest_seq(request.getSeq());
        r.setSuccess(true);
        response.setAllThreadsContinued(true);
        // r.setBody(response);
        String jsonResponse = gson.toJson(r);
        return jsonResponse;
    }

    public String createContinueResponse(ExceptionInfoManager exceptionInfoManager, String json, SendProtocolMessage send, Gson gson, AssemblyEmulator emulator, List<Integer> breakpointLineNumbers) throws Exception {
        String ContinueRequestRes = processContinueRequest(gson, json);
        ContinueRequest r1 = gson.fromJson(json, ContinueRequest.class);
        send.sendProtocolMessage(ContinueRequestRes);
        StoppedEvent se  = new StoppedEvent();
        se.setReason("breakpoint");
        se.setThreadId(r1.getArguments().getThreadId());
        Event x = new Event();
        x.setEvent("stopped");
        x.setBody(se);
        send.sendProtocolMessage(gson.toJson(x));
        int currLine = emulator.getActualLineNumber() + 1;
        int nextBreakpointLine = -1;
        for (int i = 0; i < breakpointLineNumbers.size(); i++) {
            if (currLine < breakpointLineNumbers.get(i)) {
                nextBreakpointLine = breakpointLineNumbers.get(i);
                break;
            }
        }
        if (nextBreakpointLine != -1) {
            CallEmulatorMethods callEmulatorMethods = new CallEmulatorMethods();
            callEmulatorMethods.callEmulatorNextUntilFistBreakPoint(send, exceptionInfoManager, emulator, breakpointLineNumbers, gson);
            //callEmulatorMethods.callEmulatorNextNTimes(send, nextBreakpointLine - currLine, emulator, gson, exceptionInfoManager);
        } else {
            emulator.runWholeCode();
        }
        return ContinueRequestRes;
    }

}
