package src.DebugAdapter;

import com.google.gson.Gson;
import src.DapClasses.Event.Event;
import src.DapClasses.Exceptions.ExceptionInfoRequest;
import src.DapClasses.Exceptions.ExceptionInfoResponse;
import src.DapClasses.Response;
import src.DapClasses.StoppedEvent.StoppedEvent;

import java.io.IOException;

public class ExceptionInfoManager {

    private Gson gson;
    private SendProtocolMessage send;

    public ExceptionInfoManager(){
        gson = new Gson();
        send = new SendProtocolMessage();
    }

    public String processExceptionInfoRequest(String json, String exceptionMessage) throws IOException {
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

    public void processEmulatorException(Exception e) throws IOException {
        StoppedEvent stoppedEvent = new StoppedEvent();
        stoppedEvent.setReason("exception");
        stoppedEvent.setThreadId(1);
        stoppedEvent.setText(e.getMessage());
        stoppedEvent.setDescription(e.getMessage());
        stoppedEvent.setAllThreadsStopped(false);
        stoppedEvent.setPreserveFocusHint(false);
        Event exc = new Event();
        exc.setBody(stoppedEvent);
        exc.setEvent("stopped");
        send.sendProtocolMessage(gson.toJson(exc));
    }
}
