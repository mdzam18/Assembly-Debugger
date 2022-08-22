package src.DapClasses;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class Receiver {
    FileWriter fWriter = new FileWriter(
            "D:\\FINAL\\Assembly-Debugger\\src\\Emulator\\Main\\testInputFile");
    String test0 = "Content-Length: 119\r\n" +
            "\r\n" +
            "{\r\n" +
            "    \"seq\": 153,\r\n" +
            "    \"type\": \"request\",\r\n" +
            "    \"command\": \"next\",\r\n" +
            "    \"arguments\": {\r\n" +
            "        \"threadId\": 3\r\n" +
            "    }\r\n" +
            "}";


    String test2 =
            "Content-Length: 451\r\n" +
                    "\r\n" +
                    "{" +
                    "\"command\":\"initialize\",\r\n" +
                    "\"arguments\":{\r\n" +
                    "\"clientID\":\"vscode\",\r\n" +
                    "\"clientName\":\"Visual Studio Code\",\r\n" +
                    "\"adapterID\":\"mock\",\r\n" +
                    "\"pathFormat\":\"path\",\r\n" +
                    "\"linesStartAt1\":true,\r\n" +
                    "\"columnsStartAt1\":true,\r\n" +
                    "\"supportsVariableType\":true,\r\n" +
                    "\"supportsVariablePaging\":true,\r\n" +
                    "\"supportsRunInTerminalRequest\":true,\r\n" +
                    "\"locale\":\"en-us\",\r\n" +
                    "\"supportsProgressReporting\":true,\r\n" +
                    "\"supportsInvalidatedEvent\":true,\r\n" +
                    "\"supportsMemoryReferences\":true,\r\n" +
                    "\"supportsArgsCanBeInterpretedByShell\":true\r\n" +
                    "},\r\n" +
                    "\"type\":\"request\",\r\n" +
                    "\"seq\":1\r\n" +
                    "}" +
                    "Content-Length: 79\r\n";
    String test3 = "{\n" +
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
            "}";
    private Gson gson;

    public Receiver() throws IOException {
        gson = new Gson();
    }

    private static String readHeader(Scanner scanner) {
        String current = "";
        while (true) {
            String s = "";
            while (true) {
                String s2 = scanner.next();
                // System.out.println(s2);
                if (s2.startsWith("\r")) {
                    break;
                }
                s = s + s2;
            }
            current = current + s;
            String next = scanner.next();
            if (next.startsWith("\n")) {
                //readByte აქვს და არ ვიცი უნდა თუ არა
                break;
            } else {
                current = current + "\r";
            }
        }
        return current;
    }

    private static String readRequest(Scanner scanner) {
        Map<String, String> headers = new HashMap<>();
        while (true) {
            String header = readHeader(scanner);
            String[] items = header.split(": ", 2); //?
            if (items.length != 2) {
                break;
            }
            headers.put(items[0], items[1]);
        }
        int length = Integer.parseInt(headers.get("Content-Length"));
        //System.out.println(length);
        StringBuilder rem = new StringBuilder();
        for (int i = 0; i < length; i++) {
            rem.append(scanner.next());
        }
        return rem.toString();
    }

    public void sendProtocolMessage(String json) throws IOException {
        String message = String.format("Content-Length: %d\r\n\r\n%s", json.length(), json);
        System.out.print(message);
        System.out.flush();
        fWriter.write(message);
        fWriter.flush();
    }

    public void receive() throws Exception {
        //            String message = readRequest(scanner);
//            String res = receiveProtocolMessage(message);


//            System.out.println("\n\n");
//            System.out.println(res);


//            sendProtocolMessage(hard1);
//            sendProtocolMessage(hard2);

        try {
            String hard1 = "{ \"type\": \"response\", \"request_seq\": 1, \"command\": \"initialize\", \"success\": true, \"body\": { \"supportsBreakpointLocationsRequest\": true } }";
            String hard2 = "{ \"type\": \"event\", \"event\": \"initialized\" }";
            Scanner scanner = new Scanner(System.in);
            scanner.useDelimiter("");
            while (true) {
                String message = readRequest(scanner);
                fWriter.write(message);
                fWriter.flush();
                String res = receiveProtocolMessage(message);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            fWriter.close();
        }

//        Scanner scanner = new Scanner(test2);
//        List<String> lines = new ArrayList<>();
//        while (true) {
//        int numBytes = 0;
//        //for (int i = 0; i <linesArr.size(); i++) {
//
//            while (scanner.hasNextLine()) {
//                String line = scanner.nextLine();
//                //String line = linesArr.get(i);
//                lines.add(line);
//
//                if (line.startsWith("Content-Length")) {
//                    String clean = line.replaceAll("\\D+","");
//                    numBytes = Integer.parseInt(clean);
//                }
//                if (lines.size()>2 && lines.get(lines.size() - 2).equals("")) {
//                        String content = line.substring(0,numBytes);
//                        String response = receiveProtocolMessage(content);
//                        int length = response.getBytes().length;
//                        String header = String.format("Content-Length: %d\r\n" +
//                                "\r\n", length);
//                        System.out.println(header + response);
//                        System.out.flush();
//                }
//            }
//
//        }

    }

    public String receiveProtocolMessage(String json) throws IOException {

        ProtocolMessage protocolMessage = gson.fromJson(json, ProtocolMessage.class);
        String type = protocolMessage.getType();
        switch (type) {
            case "request":
                String r1 = processRequest(json);
                return r1;
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


    public String processRequest(String json) throws IOException {

        Request request = gson.fromJson(json, Request.class);
        String command = request.getCommand();
        switch (command) {
            case "initialize":
                Response initResponse = gson.fromJson(processInitializeRequest(json), Response.class);
                initResponse.setRequest_seq(request.getSeq());
                initResponse.setSuccess(true);
                initResponse.setCommand("initialize");
                InitializedEvent initEvent = new InitializedEvent();
                sendProtocolMessage(gson.toJson(initResponse));
                sendProtocolMessage(gson.toJson(initEvent));
                return gson.toJson(initResponse) + gson.toJson(initEvent);
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
        capabilities.setSupportsBreakpointLocationsRequest(true);
        response.setBody(capabilities);
        String jsonResponse = gson.toJson(response);
        return jsonResponse;
    }
}