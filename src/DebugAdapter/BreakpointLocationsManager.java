package src.DebugAdapter;

import com.google.gson.Gson;
import src.DapClasses.Breakpoints.BreakpointLocation;
import src.DapClasses.Breakpoints.BreakpointLocationsRequest;
import src.DapClasses.Breakpoints.BreakpointLocationsResponse;
import com.google.gson.Gson;

import java.io.IOException;

public class BreakpointLocationsManager {

    //Reads request and fills breakpointLocation array
    private String processBreakpointLocationsRequest(String json, Gson gson) {
        BreakpointLocationsRequest request = gson.fromJson(json, BreakpointLocationsRequest.class);
        BreakpointLocationsResponse response = new BreakpointLocationsResponse();
        response.setRequest_seq(request.getSeq());
        response.setSuccess(true);
        BreakpointLocation[] breakpointLocations = new BreakpointLocation[1];
        breakpointLocations[0] = new BreakpointLocation();
        breakpointLocations[0].setLine(request.getArguments().getLine());
        breakpointLocations[0].setEndLine(request.getArguments().getEndLine());
        breakpointLocations[0].setColumn(request.getArguments().getColumn());
        breakpointLocations[0].setEndColumn(request.getArguments().getEndColumn());
        response.setBody(breakpointLocations);
        String jsonResponse = gson.toJson(response);
        return jsonResponse;
    }

    //creates response
    public String createBreakpointResponse(String json, Gson gson, SendProtocolMessage send) throws IOException {
        String BreakpointLocationsRes = processBreakpointLocationsRequest(json, gson);
        send.sendProtocolMessage(BreakpointLocationsRes);
        return BreakpointLocationsRes;
    }
}
