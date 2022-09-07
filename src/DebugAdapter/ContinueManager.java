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

    //Reads continue request and creates response
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

    //Calls method of callEmulatorMethods to reach second breakpoint
    public String createContinueResponse(ExceptionInfoManager exceptionInfoManager, String json, SendProtocolMessage send, Gson gson, AssemblyEmulator emulator, List<Integer> breakpointLineNumbers) throws Exception {
        String ContinueRequestRes = processContinueRequest(gson, json);
        ContinueRequest r1 = gson.fromJson(json, ContinueRequest.class);
        send.sendProtocolMessage(ContinueRequestRes);
        StoppedEvent se = new StoppedEvent();
        se.setReason("breakpoint");
        se.setThreadId(r1.getArguments().getThreadId());
        Event x = new Event();
        x.setEvent("stopped");
        x.setBody(se);
        send.sendProtocolMessage(gson.toJson(x));

        CallEmulatorMethods callEmulatorMethods = new CallEmulatorMethods();
        callEmulatorMethods.callEmulatorNextUntilFistBreakPoint(send, exceptionInfoManager, emulator, breakpointLineNumbers, gson);

        return ContinueRequestRes;
    }

}
