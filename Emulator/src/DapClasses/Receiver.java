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
        if (type.equals("request")) {
            return processRequest(json);
        } else if (type.equals("response")) {

        } else if (type.equals("event")) {

        }
        return null;
    }

    public String processEvent(String json) {
        Event event = gson.fromJson(json, Event.class);
        String command = event.getEvent();
        if (command == "initialized") {
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
        if (command == "initialize") {
            return processInitializeRequest();
        } else if (command == "setBreakpoints") {
            return processSetBreakpointsRequest();
        }
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
