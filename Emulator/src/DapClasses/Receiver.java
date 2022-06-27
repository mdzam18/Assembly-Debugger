package DapClasses;

import com.google.gson.Gson;


public class Receiver {
    private Gson gson;

    public Receiver() {
        gson = new Gson();
    }

    public String receiveProtocolMessage(String json) {

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
                return processStoppedEvent();
            case "thread":
                return processThreadEvent();
            case "terminated":
                return processTerminatedEvent();
            case "exited":
                return processExitedEvent();
            default:
                return null;
        }
    }

    private String processExitedEvent() {
        return null;
    }

    private String processTerminatedEvent() {
        return null;
    }

    private String processThreadEvent() {
        return null;
    }

    private String processStoppedEvent() {
        return null;
    }

    public String processInitializedEvent() {
        return null;
    }


    public String processRequest(String json) {

        Request request = gson.fromJson(json, Request.class);
        String command = request.getCommand();
        switch (command) {
            case "initialize":
                return processInitializeRequest();
            case "setBreakpoints":
                return processSetBreakpointsRequest();
            case "setExceptionBreakpoints":
                return processSetExceptionBreakpointsRequest();
            case "setFunctionBreakpoints":
                return processSetFunctionBreakpointsRequest();
            case "configurationDone":
                return processConfigurationDoneRequest();
            case "launch":
                return processLaunchRequest();
            case "runInTerminal":
                return processRunInTerminalRequest();
            case "attach":
                return processSetVariableRequest();
            case "setVariable":
                return processAttachRequest();
            case "threads":
                return processThreadsRequest();
            case "stackTrace":
                return processStackTraceRequest();
            case "pause":
                return processPauseRequest();
            case "continue":
                return processContinueRequest();
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

    private String processContinueRequest() {
        ContinueResponse response = new ContinueResponse();
        return null;
    }

    private String processPauseRequest() {
        PauseResponse response = new PauseResponse();
        return null;
    }

    private String processStackTraceRequest() {
        StackTraceResponse response = new StackTraceResponse();
        return null;
    }

    private String processThreadsRequest() {
        ThreadsResponse response = new ThreadsResponse();
        return null;
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

    private String processLaunchRequest() {
        LaunchResponse response = new LaunchResponse();
        return null;
    }

    private String processConfigurationDoneRequest() {
        ConfigurationDoneResponse response = new ConfigurationDoneResponse();
        return null;
    }

    private String processSetExceptionBreakpointsRequest() {
        SetExceptionBreakpointsResponse response = new SetExceptionBreakpointsResponse();
        return null;
    }

    public String processSetBreakpointsRequest() {
        SetBreakpointsResponse response = new SetBreakpointsResponse();
        return null;
    }

    public String processInitializeRequest() {
        InitializeResponse response = new InitializeResponse();
        Capabilities capabilities = new Capabilities();
        capabilities.setSupportsConfigurationDoneRequest(true);
        response.setBody(capabilities);
        String jsonResponse = gson.toJson(response);
        return jsonResponse;
    }
}
