package src.DebugAdapter;

import com.google.gson.Gson;
import src.DapClasses.Disconnects.DisconnectRequest;
import src.DapClasses.Disconnects.DisconnectResponse;

public class DisconnectResponseBuilder {
    public static String processDisconnectRequest(Gson gson, String json) {
        DisconnectRequest request = gson.fromJson(json, DisconnectRequest.class);
        DisconnectResponse response = new DisconnectResponse();
        response.setRequest_seq(request.getSeq());
        response.setSuccess(true);
        return gson.toJson(response);
    }
}
