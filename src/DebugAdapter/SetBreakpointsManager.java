package src.DebugAdapter;

import com.google.gson.Gson;
import src.DapClasses.Breakpoints.Breakpoint;
import src.DapClasses.Event.Event;
import src.DapClasses.SetBreakpoints.SetBreakpointsRequest;
import src.DapClasses.SetBreakpoints.SetBreakpointsResponse;
import src.DapClasses.Sources.SourceBreakpoint;
import src.DapClasses.StoppedEvent.StoppedEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetBreakpointsManager {
    private Breakpoint[] breakpoints;
    private List<Integer> breakpointLineNumbers;

    public Breakpoint[] getBreakpoints() {
        return breakpoints;
    }

    public SetBreakpointsManager() {
        breakpointLineNumbers = new ArrayList<>();
    }

    //Fills breakpoints array and sends response
    private String processSetBreakpointsRequest(String json, Gson gson) {
        SetBreakpointsRequest request = gson.fromJson(json, SetBreakpointsRequest.class);
        SetBreakpointsResponse response = new SetBreakpointsResponse();
        response.setRequest_seq(request.getSeq());
        response.setSuccess(true);
        SourceBreakpoint[] requestBreakpoints = request.getArguments().getBreakpoints();
        int[] requestLines = request.getArguments().getLines();
        breakpointLineNumbers = new ArrayList<>();
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

    public String createSetBreakpointResponse(String json, Gson gson, SendProtocolMessage send) throws IOException {
        String SetBreakpointsRes = processSetBreakpointsRequest(json, gson);
        //String SetBreakpointsRes = SetBreakpointsResponseBuilder.processSetBreakpointsRequest(gson, json, breakpoints,breakpointLineNumbers);
        send.sendProtocolMessage(SetBreakpointsRes);
        StoppedEvent stoppedEvent = new StoppedEvent();
        stoppedEvent.setReason("breakpoint");
        stoppedEvent.setThreadId(1);
        Event e = new Event();
        e.setBody(stoppedEvent);
        e.setEvent("stopped");
        send.sendProtocolMessage(gson.toJson(e));
        return SetBreakpointsRes;
    }

    public List<Integer> getBreakpointLineNumbers() {
        List<Integer> breakpointsLines = new ArrayList<>();
        for (int i = 0; i < breakpointLineNumbers.size(); i++) {
            breakpointsLines.add(breakpointLineNumbers.get(i));
        }
        return breakpointsLines;
    }


}
