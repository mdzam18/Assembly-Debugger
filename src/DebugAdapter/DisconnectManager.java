package src.DebugAdapter;

import com.google.gson.Gson;
import src.DapClasses.Disconnects.DisconnectRequest;
import src.DapClasses.Disconnects.DisconnectResponse;

import java.io.IOException;

public class DisconnectManager {
    private String processDisconnectRequest(Gson gson, String json) {
        DisconnectRequest request = gson.fromJson(json, DisconnectRequest.class);
        DisconnectResponse response = new DisconnectResponse();
        response.setRequest_seq(request.getSeq());
        response.setSuccess(true);
        return gson.toJson(response);
    }

    public String createDisconnectResponse(String json, Gson gson, SendProtocolMessage send) throws IOException {
        String DisconnectRequestRes = processDisconnectRequest(gson, json);
        send.sendProtocolMessage(DisconnectRequestRes);
        return DisconnectRequestRes;
    }
}
