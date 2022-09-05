package src.DebugAdapter;

import com.google.gson.Gson;
import src.DapClasses.SetBreakpoints.SetExceptionBreakpointsRequest;
import src.DapClasses.SetBreakpoints.SetExceptionBreakpointsResponse;

public  class SetExceptionBreakpointsManager {
    public String processSetExceptionBreakpointsRequest(Gson gson, String json) {
        SetExceptionBreakpointsRequest request = gson.fromJson(json, SetExceptionBreakpointsRequest.class);
        SetExceptionBreakpointsResponse response = new SetExceptionBreakpointsResponse();
        String jsonResponse = gson.toJson(response);
        return jsonResponse;
    }
}
