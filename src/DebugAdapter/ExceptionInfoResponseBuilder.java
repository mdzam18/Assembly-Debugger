package src.DebugAdapter;

import com.google.gson.Gson;
import src.DapClasses.Exceptions.ExceptionInfoRequest;
import src.DapClasses.Exceptions.ExceptionInfoResponse;
import src.DapClasses.Response;

import java.io.IOException;

public class ExceptionInfoResponseBuilder {
    public static String processExceptionInfoRequest(String exceptionMessage,Gson gson, String json) throws IOException {
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
}
