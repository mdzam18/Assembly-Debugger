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

                break;
        }
        return null;
    }

    public String processEvent(String json) {
        Event event = gson.fromJson(json, Event.class);
        String command = event.getEvent();
        if (command.equals("initialized")) {
            return processInitializedEvent();
        } else {

        }
        return null;
    }

    public String processInitializedEvent() {
        return null;
    }


    public String processRequest(String json) {

        Request request = gson.fromJson(json, Request.class);
        String command = request.getCommand();
        switch (command) {
            case "initialize": return processInitializeRequest();
            case "setBreakpoints": return processSetBreakpointsRequest();
            case "setExceptionBreakpoints": return processSetExceptionBreakpointsRequest();
            case "configurationDone": return processConfigurationDoneRequest();
            case "launch": return processLaunchRequest();
            default: return null;
        }
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
        SetExceptionBreakpointsResponse  response = new SetExceptionBreakpointsResponse();
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
