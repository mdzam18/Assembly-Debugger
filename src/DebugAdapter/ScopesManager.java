package src.DebugAdapter;

import com.google.gson.Gson;
import src.DapClasses.Response;
import src.DapClasses.Scopes.Scope;
import src.DapClasses.Scopes.ScopesRequest;
import src.DapClasses.Scopes.ScopesResponse;

import java.io.IOException;

public class ScopesManager {

    private String processScopesRequest(String json, Gson gson) {
        ScopesRequest request = gson.fromJson(json, ScopesRequest.class);
        ScopesResponse response = new ScopesResponse();
        Scope[] scopes = new Scope[3];
        scopes[0] = new Scope();
        scopes[0].setExpensive(false);
        scopes[0].setName("Registers");
        scopes[0].setVariablesReference(10);
        scopes[1] = new Scope();
        scopes[1].setExpensive(false);
        scopes[1].setName("Stack Frame");
        scopes[1].setVariablesReference(11);
        scopes[2] = new Scope();
        scopes[2].setExpensive(false);
        scopes[2].setName("Special Registers");
        scopes[2].setVariablesReference(12);
        response.setScopes(scopes);
        Response r = new Response();
        r.setCommand("scopes");
        r.setRequest_seq(request.getSeq());
        r.setSuccess(true);
        r.setBody(response);
        String jsonResponse = gson.toJson(r);
        return jsonResponse;
    }

    public String createScopesResponse(String json, Gson gson, SendProtocolMessage send) throws IOException {
        String ScopesRequestRes = processScopesRequest(json, gson);
        send.sendProtocolMessage(ScopesRequestRes);
        return ScopesRequestRes;
    }

}
