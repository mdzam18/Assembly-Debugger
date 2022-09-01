package src.DebugAdapter;

import com.google.gson.Gson;
import src.DapClasses.SetBreakpoints.SetFunctionBreakpointsRequest;
import src.DapClasses.SetBreakpoints.SetFunctionBreakpointsResponse;

public class SetFunctionBreakpointsResponseBuilder {
    public static String processSetFunctionBreakpointsRequest(Gson gson, String json) {
        SetFunctionBreakpointsRequest request = gson.fromJson(json, SetFunctionBreakpointsRequest.class);
        SetFunctionBreakpointsResponse response = new SetFunctionBreakpointsResponse();
        response.setRequest_seq(request.getSeq());
        response.setSuccess(true);
        String jsonResponse = gson.toJson(response);
        return jsonResponse;
    }
}
