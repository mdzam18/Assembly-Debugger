package src.DebugAdapter;

import com.google.gson.Gson;
import src.DapClasses.Nexts.NextRequest;
import src.DapClasses.Nexts.NextResponse;

public class NextResponseBuilder {
    public static String processNextRequest(Gson gson, String json) {
        NextRequest request = gson.fromJson(json, NextRequest.class);
        NextResponse response = new NextResponse();
        response.setRequest_seq(request.getSeq());
        response.setSuccess(true);
        String jsonResponse = gson.toJson(response);
        return jsonResponse;
    }
}
