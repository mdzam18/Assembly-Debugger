package src.DebugAdapter;

import com.google.gson.Gson;
import src.DapClasses.Event.Event;
import src.DapClasses.Nexts.NextRequest;
import src.DapClasses.Nexts.NextResponse;
import src.DapClasses.StoppedEvent.StoppedEvent;
import src.Emulator.AssemblyEmulator.AssemblyEmulator;

public class NextManager {
    private String processNextRequest(String json, Gson gson) {
        NextRequest request = gson.fromJson(json, NextRequest.class);
        NextResponse response = new NextResponse();
        response.setRequest_seq(request.getSeq());
        response.setSuccess(true);
        String jsonResponse = gson.toJson(response);
        return jsonResponse;
    }

    public String createNextResponse(ExceptionInfoManager exceptionInfoManager, String json, Gson gson, SendProtocolMessage send, AssemblyEmulator emulator) throws Exception {
        String NextRequestRes = processNextRequest(json, gson);
        send.sendProtocolMessage(NextRequestRes);
        StoppedEvent stoppedEvent1 = new StoppedEvent();
        stoppedEvent1.setReason("step");
        stoppedEvent1.setThreadId(1);
        Event e1 = new Event();
        e1.setBody(stoppedEvent1);
        e1.setEvent("stopped");
        send.sendProtocolMessage(gson.toJson(e1));
        CallEmulatorMethods callEmulatorMethods = new CallEmulatorMethods();
        callEmulatorMethods.callEmulatorNextNTimes(1, emulator, gson, exceptionInfoManager);
        return NextRequestRes;
    }

}
