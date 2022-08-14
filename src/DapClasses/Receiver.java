package src.DapClasses;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public  class Receiver {
    private Gson gson;

    public Receiver() {
        gson = new Gson();
    }
    public  void receive() throws Exception{
        FileWriter fWriter = new FileWriter(
                "/home/nroga/Final/Assembly-Debugger/src/Emulator/Main/testInputFile");

        try(Scanner scanner = new Scanner(System.in);){
            String s;
            int counter = 0;
            int numBytes = 0;
            List<String> lines = new ArrayList<String>() ;
            while (counter <20){
                counter++;
                s = scanner.nextLine();
                lines.add(s);
                if(s.contains("Content-Length: ")){
                    numBytes = Integer.parseInt(s.split(" ")[1]);
                }
                if(lines.size() > 2 && lines.get(lines.size()-2).equals("\\r\\n")){

                    String response  = receiveProtocolMessage(s.substring(numBytes));
                    int length = response.getBytes().length;
                    String header = String.format("Content-Length: %d\\r\\n\n" +
                            "\\r\\n", length);
                    System.out.println(header + response);
                    System.out.flush();
                }
                fWriter.write(s);
                fWriter.flush();
                if(counter %3 == 0){

                    String response  = receiveProtocolMessage(s);
                    int length = response.getBytes().length;
                    String header = String.format("Content-Length: %d\\r\\n\n" +
                            "\\r\\n", length);
                    System.out.println(header + response);
                    System.out.flush();
                }
            }
        }
    }
    public String receiveProtocolMessage(String json) {
        String q = "Content-Length: 451\n" +
                "{\n" +
                "\"command\":\"initialize\",\n" +
                "\"arguments\":{\n" +
                "\"clientID\":\"vscode\",\n" +
                "\"clientName\":\"Visual Studio Code\",\n" +
                "\"adapterID\":\"mock\",\n" +
                "\"pathFormat\":\"path\",\n" +
                "\"linesStartAt1\":true,\n" +
                "\"columnsStartAt1\":true,\n" +
                "\"supportsVariableType\":true,\n" +
                "\"supportsVariablePaging\":true,\n" +
                "\"supportsRunInTerminalRequest\":true,\n" +
                "\"locale\":\"en-us\",\n" +
                "\"supportsProgressReporting\":true,\n" +
                "\"supportsInvalidatedEvent\":true,\n" +
                "\"supportsMemoryReferences\":true,\n" +
                "\"supportsArgsCanBeInterpretedByShell\":true\n" +
                "},\n" +
                "\"type\":\"request\",\n" +
                "\"seq\":1\n" +
                "}\n" +
                "Content-Length: 79";
        ProtocolMessage protocolMessage = gson.fromJson(json, ProtocolMessage.class);
        String type = protocolMessage.getType();
        System.out.println(type);
        switch (type) {
            case "request":
                return processRequest(json);
            case "response":

                break;
            case "event":
                return processEvent(json);

        }
        return null;
    }

    public String processEvent(String json) {
        Event event = gson.fromJson(json, Event.class);
        String command = event.getEvent();
        switch (command) {
            case "initialized":
                return processInitializedEvent();
            case "stopped":
                return processStoppedEvent(json);
            case "thread":
                return processThreadEvent();
            case "terminated":
                return processTerminatedEvent(json);
            case "exited":
                return processExitedEvent();
            default:
                return null;
        }
    }



    public String processInitializedEvent() {
        return null;
    }


    public String processRequest(String json) {

        Request request = gson.fromJson(json, Request.class);
        String command = request.getCommand();
        switch (command) {
            case "initialize":
                return processInitializeRequest(json);
            case "setBreakpoints":
                return processSetBreakpointsRequest(json);
            case "setExceptionBreakpoints":
                return processSetExceptionBreakpointsRequest(json);
            case "setFunctionBreakpoints":
                return processSetFunctionBreakpointsRequest();
            case "configurationDone":
                return processConfigurationDoneRequest(json);
            case "launch":
                return processLaunchRequest(json);
            case "runInTerminal":
                return processRunInTerminalRequest();
            case "attach":
                return processSetVariableRequest();
            case "setVariable":
                return processAttachRequest();
            case "threads":
                return processThreadsRequest(json);
            case "stackTrace":
                return processStackTraceRequest(json);
            case "scopes":
                return processScopesRequest(json);
            case "variables":
                return processVariablesRequest(json);
            case "pause":
                return processPauseRequest();
            case "continue":
                return processContinueRequest(json);
            case "next":
                return processNextRequest();
            case "stepIn":
                return processStepInRequest();
            case "stepOut":
                return processStepOutRequest();
            case "terminate":
                return processTerminateRequest();
            case "disconnect":
                return processDisconnectRequest();
            default:
                return null;
        }
    }
    private String processExitedEvent() {
        return null;
    }

    private String processTerminatedEvent(String json) {
        return null;
    }

    private String processThreadEvent() {
        return null;
    }

    private String processStoppedEvent(String json) {
        StoppedEvent request = gson.fromJson(json, StoppedEvent.class);
        return null;
    }
    private String processVariablesRequest(String json) {
        VariablesRequest request = gson.fromJson(json, VariablesRequest.class);
        VariablesResponse response = new VariablesResponse();
        String jsonResponse = gson.toJson(response);
        return jsonResponse;
    }
    private String processScopesRequest(String json) {
        ScopesRequest request = gson.fromJson(json, ScopesRequest.class);
        ScopesResponse response = new ScopesResponse();
        String jsonResponse = gson.toJson(response);
        return jsonResponse;
    }
    private String processDisconnectRequest() {
        DisconnectResponse response = new DisconnectResponse();
        return null;
    }

    private String processTerminateRequest() {
        TerminateResponse response = new TerminateResponse();
        return null;
    }

    private String processStepOutRequest() {
        StepOutResponse response = new StepOutResponse();
        return null;
    }

    private String processStepInRequest() {
        StepInResponse response = new StepInResponse();
        return null;
    }

    private String processNextRequest() {
        NextResponse response = new NextResponse();
        return null;
    }

    private String processContinueRequest(String json) {
        ContinueRequest request = gson.fromJson(json, ContinueRequest.class);
        ContinueResponse response = new ContinueResponse();
        return null;
    }

    private String processPauseRequest() {
        PauseResponse response = new PauseResponse();
        return null;
    }

    private String processStackTraceRequest(String json) {
        StackTraceRequest request = gson.fromJson(json, StackTraceRequest.class);
        StackTraceResponse response = new StackTraceResponse();
        String jsonResponse = gson.toJson(response);
        return jsonResponse;
    }

    private String processThreadsRequest(String json) {
        ThreadsRequest request = gson.fromJson(json, ThreadsRequest.class);
        ThreadsResponse response = new ThreadsResponse();
        String jsonResponse = gson.toJson(response);
        return jsonResponse;
    }

    private String processSetVariableRequest() {
        SetVariableResponse response = new SetVariableResponse();
        return null;
    }

    private String processSetFunctionBreakpointsRequest() {
        SetFunctionBreakpointsResponse response = new SetFunctionBreakpointsResponse();
        return null;
    }

    private String processAttachRequest() {
        AttachResponse response = new AttachResponse();
        return null;
    }

    private String processRunInTerminalRequest() {
        RunInTerminalResponse response = new RunInTerminalResponse();
        return null;
    }

    private String processLaunchRequest(String json) {
        LaunchRequest request = gson.fromJson(json, LaunchRequest.class);
        LaunchResponse response = new LaunchResponse();
        String jsonResponse = gson.toJson(response);
        return jsonResponse;
    }

    private String processConfigurationDoneRequest(String json) {
        ConfigurationDoneRequest request = gson.fromJson(json, ConfigurationDoneRequest.class);
        ConfigurationDoneResponse response = new ConfigurationDoneResponse();
        String jsonResponse = gson.toJson(response);
        return jsonResponse;
    }

    private String processSetExceptionBreakpointsRequest(String json) {
        SetExceptionBreakpointsRequest request = gson.fromJson(json, SetExceptionBreakpointsRequest.class);
        SetExceptionBreakpointsResponse response = new SetExceptionBreakpointsResponse();
        String jsonResponse = gson.toJson(response);
        return jsonResponse;
    }

    public String processSetBreakpointsRequest(String json) {
        SetBreakpointsRequest request = gson.fromJson(json, SetBreakpointsRequest.class);
        SetBreakpointsResponse response = new SetBreakpointsResponse();
        String jsonResponse = gson.toJson(response);
        return jsonResponse;
    }

    public String processInitializeRequest(String json) {
        InitializeRequest request = gson.fromJson(json, InitializeRequest.class);
        InitializeResponse response = new InitializeResponse();
        Capabilities capabilities = new Capabilities();
        //capabilities.setSupportsConfigurationDoneRequest(true);
        capabilities.setSupportsBreakpointLocationsRequest(true);
        response.setBody(capabilities);
        String jsonResponse = gson.toJson(response);
        return jsonResponse;
    }
}
