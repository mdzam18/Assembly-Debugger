package src.DebugAdapter;

import com.google.gson.Gson;
import src.DapClasses.Capabilities.Capabilities;
import src.DapClasses.Initialize.InitializeRequest;
import src.DapClasses.Initialize.InitializeResponse;
import src.DapClasses.Request;
import src.DapClasses.Response;

public class InitializeResponseBuilder {

    public static String processInitializeRequest(Gson gson, String json) {
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
}
