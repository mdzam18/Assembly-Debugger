package src.DebugAdapter;

import com.google.gson.Gson;
import src.DapClasses.Capabilities.Capabilities;
import src.DapClasses.Initialize.InitializeResponse;
import src.DapClasses.Initialize.InitializedEvent;
import src.DapClasses.Request;
import src.DapClasses.Response;

public class InitializeManager {

    private Gson gson;
    private SendProtocolMessage send;

    public InitializeManager(){
        gson = new Gson();
        send = new SendProtocolMessage();
    }

    private String processInitializeRequest(String json) {
        Request request = gson.fromJson(json, Request.class);
        InitializeResponse response = new InitializeResponse();
        Capabilities capabilities = new Capabilities();
        capabilities.setSupportsBreakpointLocationsRequest(true);
        capabilities.setSupportsExceptionInfoRequest(true);
        capabilities.setSupportsSingleThreadExecutionRequests(true);
        response.setBody(capabilities);
        Response r = gson.fromJson(gson.toJson(response), Response.class);
        r.setRequest_seq(request.getSeq());
        r.setSuccess(true);
        r.setCommand("initialize");
        return gson.toJson(r);
    }

    public String createInitializeResponse(Request request, String json){
        Response initResponse = gson.fromJson(processInitializeRequest(json), Response.class);
        initResponse.setRequest_seq(request.getSeq());
        initResponse.setSuccess(true);
        initResponse.setCommand("initialize");
        InitializedEvent initEvent = new InitializedEvent();
        send.sendProtocolMessage(gson.toJson(initResponse));
        send.sendProtocolMessage(gson.toJson(initEvent));
        return gson.toJson(initResponse) + gson.toJson(initEvent);
    }
}