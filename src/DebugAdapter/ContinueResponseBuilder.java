package src.DebugAdapter;

import com.google.gson.Gson;
import src.DapClasses.Continues.ContinueRequest;
import src.DapClasses.Continues.ContinueResponse;
import src.DapClasses.Response;

public class ContinueResponseBuilder {
    public static String processContinueRequest(Gson gson, String json) {
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

}
