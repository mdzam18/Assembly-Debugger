package src.DebugAdapter;

import com.google.gson.Gson;
import src.DapClasses.Breakpoints.Breakpoint;
import src.DapClasses.SetBreakpoints.SetBreakpointsRequest;
import src.DapClasses.SetBreakpoints.SetBreakpointsResponse;
import src.DapClasses.Sources.SourceBreakpoint;

import java.io.IOException;
import java.util.List;

public class SetBreakpointsResponseBuilder {
    public static String processSetBreakpointsRequest(Gson gson, String json,Breakpoint[] breakpoints, List<Integer> breakpointLineNumbers) throws IOException {
        SetBreakpointsRequest request = gson.fromJson(json, SetBreakpointsRequest.class);
        SetBreakpointsResponse response = new SetBreakpointsResponse();
        response.setRequest_seq(request.getSeq());
        response.setSuccess(true);
        SourceBreakpoint[] requestBreakpoints = request.getArguments().getBreakpoints();
        int[] requestLines = request.getArguments().getLines();
        for (int i = 0; i < requestLines.length; i++) {
            breakpointLineNumbers.add(requestLines[i]);
        }
        breakpoints = new Breakpoint[requestBreakpoints.length];
        for (int i = 0; i < requestBreakpoints.length; i++) {
            breakpoints[i] = new Breakpoint();
            breakpoints[i].setLine(requestBreakpoints[i].getLine());
            //breakpointLineNumbers.add(requestBreakpoints[i].getLine());
            breakpoints[i].setVerified(true);
        }
        response.setBody(breakpoints);
        String jsonResponse = gson.toJson(response);
        return jsonResponse;
    }


}
