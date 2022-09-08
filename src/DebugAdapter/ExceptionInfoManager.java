package src.DebugAdapter;

import com.google.gson.Gson;
import src.DapClasses.Event.Event;
import src.DapClasses.Exceptions.ExceptionInfoRequest;
import src.DapClasses.Exceptions.ExceptionInfoResponse;
import src.DapClasses.BaseClasses.Response;
import src.DapClasses.StoppedEvent.StoppedEvent;

import java.io.IOException;

public class ExceptionInfoManager {
    private String exceptionMessage;

    //Creates exception message
    private String processExceptionInfoRequest(String json, Gson gson) throws IOException {
        ExceptionInfoRequest request = gson.fromJson(json, ExceptionInfoRequest.class);
        Response r = new Response();
        r.setCommand("exceptionInfo");
        r.setRequest_seq(request.getSeq());
        r.setSuccess(true);
        ExceptionInfoResponse response = new ExceptionInfoResponse();
        response.setExceptionId("1");
        response.setDescription(exceptionMessage);
        response.setBreakMode("always");
        r.setBody(response);
        String jsonResponse = gson.toJson(r);
        return jsonResponse;
    }

    //Sends stopped event
    public void processEmulatorException(Exception e, Gson gson, SendProtocolMessage send) throws IOException {
        StoppedEvent stoppedEvent = new StoppedEvent();
        stoppedEvent.setReason("exception");
        stoppedEvent.setThreadId(1);
        stoppedEvent.setText(e.getMessage());
        exceptionMessage = e.getMessage();
        stoppedEvent.setDescription(e.getMessage());
        stoppedEvent.setAllThreadsStopped(false);
        stoppedEvent.setPreserveFocusHint(false);
        Event exc = new Event();
        exc.setBody(stoppedEvent);
        exc.setEvent("stopped");
        send.sendProtocolMessage(gson.toJson(exc));
    }

    public String createExceptionInfoResponse(String json, Gson gson, SendProtocolMessage send) throws IOException {
        String exceptionInfoRequestRes = processExceptionInfoRequest(json, gson);
        send.sendProtocolMessage(exceptionInfoRequestRes);
        return exceptionInfoRequestRes;
    }
}
