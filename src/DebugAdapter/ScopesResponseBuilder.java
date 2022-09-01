package src.DebugAdapter;

import com.google.gson.Gson;
import src.DapClasses.Response;
import src.DapClasses.Scopes.Scope;
import src.DapClasses.Scopes.ScopesRequest;
import src.DapClasses.Scopes.ScopesResponse;

public class ScopesResponseBuilder {
    public static String processScopesRequest(Gson gson, String json) {
        ScopesRequest request = gson.fromJson(json, ScopesRequest.class);
        ScopesResponse response = new ScopesResponse();
        Scope[] scopes = new Scope[2];
        scopes[0] = new Scope();
        scopes[0].setExpensive(false);
        scopes[0].setName("Registers");
        scopes[0].setVariablesReference(10);
        scopes[1] = new Scope();
        scopes[1].setExpensive(false);
        scopes[1].setName("Stack Frame");
        scopes[1].setVariablesReference(11);
        response.setScopes(scopes);
        Response r = new Response();
        r.setCommand("scopes");
        r.setRequest_seq(request.getSeq());
        r.setSuccess(true);
        r.setBody(response);
        String jsonResponse = gson.toJson(r);
        return jsonResponse;
    }

}
