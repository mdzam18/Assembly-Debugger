package src.DapClasses;

import com.google.gson.Gson;
import src.Emulator.AssemblyEmulator.AssemblyEmulator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class Receiver {
    private FileWriter fWriter = new FileWriter(
            "/Users/mariami/Desktop/Assembly-Debugger/src/Emulator/Main/testInputFile");
    private FileWriter fWriterEmulator = new FileWriter(
            "/Users/mariami/Desktop/Assembly-Debugger/src/Emulator/Main/testEmulator.txt");
    //D:\FINAL\Assembly-Debugger\src\Emulator\Main\testInputFile
    ///home/nroga/Final/Assembly-Debugger/src/Emulator/Main/testInputFile
    private String test0 = "Content-Length: 119\r\n" +
            "\r\n" +
            "{\r\n" +
            "    \"seq\": 153,\r\n" +
            "    \"type\": \"request\",\r\n" +
            "    \"command\": \"next\",\r\n" +
            "    \"arguments\": {\r\n" +
            "        \"threadId\": 3\r\n" +
            "    }\r\n" +
            "}";


    private String test2 =
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
    private String test3 = "{\n" +
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
    private String name;
    private String program;
    private AssemblyEmulator emulator;
    private List<Integer> breakpointLineNumbers;

    private String exceptionMessage;

    public Receiver() throws Exception {
        gson = new Gson();
        breakpointLineNumbers = new ArrayList<>();

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
                //readByte
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
        fWriter.write("\n Sent \n\n");
        fWriter.write(json);
        fWriter.flush();
    }

    public void receive() throws Exception {
        //String t = "{\"command\":\"breakpointLocations\",\"arguments\":{\"source\":{\"name\":\"readme.md\",\"path\":\"/home/nroga/Final/Assembly-Debugger/VS_Code/vscode-mock-debug/sampleWorkspace/readme.md\"},\"line\":4},\"type\":\"request\",\"seq\":5}";
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("");
        try {
            //String res1 = receiveProtocolMessage(t);
            while (true) {
                String message = readRequest(scanner);
                fWriter.write("\n Received \n\n");
                fWriter.write(message);
                fWriter.flush();
                String res = receiveProtocolMessage(message);
            }
        } catch (Exception e) {
//            StoppedEvent stoppedEvent = new StoppedEvent();
//            stoppedEvent.setReason("exception");
//            stoppedEvent.setThreadId(1);
//            stoppedEvent.setText(e.getMessage());
//            stoppedEvent.setDescription(e.getMessage());
//            Event exc = new Event();
//
//            sendProtocolMessage(gson.toJson(exc));
//            exc.setBody(stoppedEvent);
//            exc.setEvent("stopped");
//
//            String message = readRequest(scanner);
//            fWriter.write("\n Received2 \n\n");
//            fWriter.write(message.length() + " length\n");
//            fWriter.write("message " + message);
//            fWriter.flush();
//            fWriter.write("\n exception \n\n");
//            fWriter.write("exception "  + e.getMessage());
//            fWriter.flush();
            //System.out.println(e.getMessage());
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

    public String receiveProtocolMessage(String json) throws Exception {
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

    public void callEmulatorNextNTimes(int num) throws Exception {
        for (int i = 0; i < num; i++) {
            fWriter.write("\n\n\n Next \n\n\n\n");
            fWriter.flush();
            try {
               emulator.next();
            } catch (Exception e){
                StoppedEvent stoppedEvent = new StoppedEvent();
                stoppedEvent.setReason("exception");
                stoppedEvent.setThreadId(1);
                stoppedEvent.setText(e.getMessage());
                stoppedEvent.setDescription(e.getMessage());
                Event exc = new Event();
                exc.setBody(stoppedEvent);
                exc.setEvent("stopped");
                sendProtocolMessage(gson.toJson(exc));
                exceptionMessage = e.getMessage();
                fWriter.write("\n exception \n\n");
                fWriter.write(e.getMessage());
                fWriter.flush();
            }
        }
    }

    public String processRequest(String json) throws Exception {
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
                String SetBreakpointsRes = processSetBreakpointsRequest(json);
                sendProtocolMessage(SetBreakpointsRes);
                StoppedEvent stoppedEvent = new StoppedEvent();
                stoppedEvent.setReason("breakpoint");
                stoppedEvent.setThreadId(1);
                Event e = new Event();
                e.setBody(stoppedEvent);
                e.setEvent("stopped");
                sendProtocolMessage(gson.toJson(e));
                return SetBreakpointsRes;
            case "setExceptionBreakpoints":
                return processSetExceptionBreakpointsRequest(json);
            case "setFunctionBreakpoints":
                String SetFunctionBreakpointsRes = processSetFunctionBreakpointsRequest(json);
                sendProtocolMessage(SetFunctionBreakpointsRes);
                return SetFunctionBreakpointsRes;
            case "configurationDone":
                String ConfigurationDoneRes = processConfigurationDoneRequest(json);
                sendProtocolMessage(ConfigurationDoneRes);
                return ConfigurationDoneRes;
            case "launch":
                String LaunchRes = processLaunchRequest(json);
                sendProtocolMessage(LaunchRes);
                return LaunchRes;
            case "breakpointLocations":
                String BreakpointLocationsRes = processBreakpointLocationsRequest(json);
                sendProtocolMessage(BreakpointLocationsRes);
                return BreakpointLocationsRes;
            case "runInTerminal":
                return processRunInTerminalRequest();
            case "attach":
                return processSetVariableRequest();
            case "setVariable":
                return processAttachRequest();
            case "threads":
                String ThreadsRes = processThreadsRequest(json);
                sendProtocolMessage(ThreadsRes);
                return ThreadsRes;
            case "stackTrace":
                String StackTraceRes = processStackTraceRequest(json);
                if (emulator.getCurrentLine() == 0) {
                    callEmulatorNextNTimes(breakpointLineNumbers.get(0) - 1);
                }
                sendProtocolMessage(StackTraceRes);
                return StackTraceRes;
            case "scopes":
                String ScopesRequestRes = processScopesRequest(json);
                sendProtocolMessage(ScopesRequestRes);
                return ScopesRequestRes;
            case "variables":
                String VariablesRequestRes = processVariablesRequest(json);
                sendProtocolMessage(VariablesRequestRes);
                return VariablesRequestRes;
            case "pause":
                return processPauseRequest();
            case "continue":
                String ContinueRequestRes = processContinueRequest(json);
                sendProtocolMessage(ContinueRequestRes);
                int currLine = emulator.getCurrentLine() + 1;
                int nextBreakpointLine = -1;
                for (int i = 0; i < breakpointLineNumbers.size(); i++) {
                    if (currLine < breakpointLineNumbers.get(i)) {
                        nextBreakpointLine = breakpointLineNumbers.get(i);
                        break;
                    }
                }
                if (nextBreakpointLine != -1) {
                    callEmulatorNextNTimes(nextBreakpointLine - currLine);
                } else {
                    emulator.debug();
                }
                return ContinueRequestRes;
            case "next":
                String NextRequestRes = processNextRequest(json);
                sendProtocolMessage(NextRequestRes);
                StoppedEvent stoppedEvent1 = new StoppedEvent();
                stoppedEvent1.setReason("step");
                stoppedEvent1.setThreadId(1);
                Event e1 = new Event();
                e1.setBody(stoppedEvent1);
                e1.setEvent("stopped");
                sendProtocolMessage(gson.toJson(e1));
                callEmulatorNextNTimes(1);
                return NextRequestRes;
            case "stepIn":
                return processStepInRequest();
            case "stepOut":
                return processStepOutRequest();
            case "terminate":
                return processTerminateRequest();
            case "disconnect":
                String DisconnectRequestRes = processDisconnectRequest(json);
                sendProtocolMessage(DisconnectRequestRes);
                return DisconnectRequestRes;
            case "exceptionInfo":
                ExceptionInfoResponse exceptionInfoResponse = new ExceptionInfoResponse();
                exceptionInfoResponse.setExceptionId("1");
                exceptionInfoResponse.setDescription(exceptionMessage);
               // ExceptionBreakMode mode = new ExceptionBreakMode();
                //mode.setMode("always");
                exceptionInfoResponse.setBreakMode("always");
                sendProtocolMessage(gson.toJson(exceptionInfoResponse));
                return "";//gson.toJson(initResponse) + gson.toJson(initEvent);
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

    private String processVariablesRequest(String json) throws IOException {
        VariablesRequest request = gson.fromJson(json, VariablesRequest.class);
        VariablesResponse response = new VariablesResponse();
        Map<String, Integer> variablesMap = emulator.getRegisters();
        Variable[] variables = new Variable[variablesMap.size()];
        int counter = 0;
        for (String key : variablesMap.keySet()) {
            Variable v = new Variable();
            v.setName(key);
            v.setValue(String.valueOf(variablesMap.get(key)));
            fWriterEmulator.write(key);
            fWriterEmulator.write(variablesMap.get(key));
            fWriterEmulator.flush();
            variables[counter] = v;
            counter++;
        }
        response.setVariables(variables);
        Response r = new Response();
        r.setCommand("variables");
        r.setRequest_seq(request.getSeq());
        r.setSuccess(true);
        r.setBody(response);
        String jsonResponse = gson.toJson(r);
        return jsonResponse;
    }

    private String processScopesRequest(String json) {
        ScopesRequest request = gson.fromJson(json, ScopesRequest.class);
        ScopesResponse response = new ScopesResponse();
        Scope[] scopes = new Scope[1];
        scopes[0] = new Scope();
        scopes[0].setExpensive(false);
        scopes[0].setName("Registers");
        scopes[0].setVariablesReference(1000);
//        scopes[1] = new Scope();
//        scopes[1].setExpensive(false);
//        scopes[1].setName("Stack Frame");
//        scopes[1].setVariablesReference(1000);
        response.setScopes(scopes);
        Response r = new Response();
        r.setCommand("scopes");
        r.setRequest_seq(request.getSeq());
        r.setSuccess(true);
        r.setBody(response);
        String jsonResponse = gson.toJson(r);
        return jsonResponse;
    }

    private String processDisconnectRequest(String json) {
        DisconnectRequest request = gson.fromJson(json, DisconnectRequest.class);
        DisconnectResponse response = new DisconnectResponse();
        response.setRequest_seq(request.getSeq());
        response.setSuccess(true);
        return gson.toJson(response);
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

    private String processNextRequest(String json) {
        NextRequest request = gson.fromJson(json, NextRequest.class);
        NextResponse response = new NextResponse();
        response.setRequest_seq(request.getSeq());
        response.setSuccess(true);
        String jsonResponse = gson.toJson(response);
        return jsonResponse;
    }

    private String processContinueRequest(String json) {
        ContinueRequest request = gson.fromJson(json, ContinueRequest.class);
        ContinueResponse response = new ContinueResponse();
        Response r = new Response();
        r.setCommand("continue");
        r.setRequest_seq(request.getSeq());
        r.setSuccess(true);
        response.setAllThreadsContinued(true);
        // r.setBody(response);
        String jsonResponse = gson.toJson(r);
        return jsonResponse;
    }

    private String processPauseRequest() {
        PauseResponse response = new PauseResponse();
        return null;
    }

    private String processStackTraceRequest(String json) {
        StackTraceRequest request = gson.fromJson(json, StackTraceRequest.class);
        Response r = new Response();
        r.setSuccess(true);
        r.setRequest_seq(request.getSeq());
        r.setCommand("stackTrace");
        StackTraceResponse response = new StackTraceResponse();
        ArrayList<String> callstack = emulator.getCallStack();
        //if(callstack.size() == 0){
        //  processTerminateRequest();
        //anu morcha da unda shevawyvetino
        // }
        StackFrame[] stackFrames = new StackFrame[callstack.size()];
        for (int i = 0; i < callstack.size(); i++) {
            stackFrames[i] = new StackFrame();
            stackFrames[i].setColumn(0);
            stackFrames[i].setId(0);
            if (emulator.getCurrentLine() == 0) {
                stackFrames[i].setLine(breakpointLineNumbers.get(0));
            } else {
                stackFrames[i].setLine(emulator.getCurrentLine() + 1);
            }
            stackFrames[i].setName(callstack.get(i));
            stackFrames[i].setSource(createSource());
        }
        response.setStackFrames(stackFrames);
        response.setTotalFrames(response.getStackFrames().length);
        r.setBody(response);
        String jsonResponse = gson.toJson(r);
        return jsonResponse;
    }

    private Source createSource() {
        Source source = new Source();
        source.setName(name);
        source.setAdapterData("mock-adapter-data");
        source.setSourceReference(0);
        source.setPath(program);
        return source;
    }

    private String processThreadsRequest(String json) {
        ThreadsRequest request = gson.fromJson(json, ThreadsRequest.class);
        ThreadsResponse response = new ThreadsResponse();
        Thread[] threads = new Thread[1];
        threads[0] = new Thread();
        threads[0].setId(1);
        threads[0].setName("thread 1");
        response.setThreads(threads);
        Response r = new Response();
        r.setBody(response);
        r.setCommand("threads");
        r.setRequest_seq(request.getSeq());
        r.setSuccess(true);
        String jsonResponse = gson.toJson(r);
        return jsonResponse;
    }

    private String processSetVariableRequest() {
        SetVariableResponse response = new SetVariableResponse();
        return null;
    }

    private String processSetFunctionBreakpointsRequest(String json) {
        SetFunctionBreakpointsRequest request = gson.fromJson(json, SetFunctionBreakpointsRequest.class);
        SetFunctionBreakpointsResponse response = new SetFunctionBreakpointsResponse();
        response.setRequest_seq(request.getSeq());
        response.setSuccess(true);
        String jsonResponse = gson.toJson(response);
        return jsonResponse;
    }

    private String processAttachRequest() {
        AttachResponse response = new AttachResponse();
        return null;
    }

    private String processRunInTerminalRequest() {
        RunInTerminalResponse response = new RunInTerminalResponse();
        return null;
    }

    private String processBreakpointLocationsRequest(String json) {
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

    private String processLaunchRequest(String json) throws Exception {
        LaunchRequest request = gson.fromJson(json, LaunchRequest.class);
        name = request.getArguments().getName();
        program = request.getArguments().getProgram();
        String arr[] = new String[1];
        arr[0] = program;
        emulator = new AssemblyEmulator(arr);
        LaunchResponse response = new LaunchResponse();
        response.setRequest_seq(request.getSeq());
        response.setSuccess(true);
        String jsonResponse = gson.toJson(response);
        return jsonResponse;
    }

    private String processConfigurationDoneRequest(String json) {
        ConfigurationDoneRequest request = gson.fromJson(json, ConfigurationDoneRequest.class);
        ConfigurationDoneResponse response = new ConfigurationDoneResponse();
        response.setRequest_seq(request.getSeq());
        response.setSuccess(true);
        String jsonResponse = gson.toJson(response);
        return jsonResponse;
    }

    private String processSetExceptionBreakpointsRequest(String json) {
        SetExceptionBreakpointsRequest request = gson.fromJson(json, SetExceptionBreakpointsRequest.class);
        SetExceptionBreakpointsResponse response = new SetExceptionBreakpointsResponse();
        String jsonResponse = gson.toJson(response);
        return jsonResponse;
    }

    public String processSetBreakpointsRequest(String json) throws IOException {
        SetBreakpointsRequest request = gson.fromJson(json, SetBreakpointsRequest.class);
        SetBreakpointsResponse response = new SetBreakpointsResponse();
        response.setRequest_seq(request.getSeq());
        response.setSuccess(true);
        SourceBreakpoint[] requestBreakpoints = request.getArguments().getBreakpoints();
        int[] requestLines = request.getArguments().getLines();
        for (int i = 0; i < requestLines.length; i++) {
            breakpointLineNumbers.add(requestLines[i]);
        }
        Breakpoint[] breakpoints = new Breakpoint[requestBreakpoints.length];
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

    public String processInitializeRequest(String json) {
        InitializeRequest request = gson.fromJson(json, InitializeRequest.class);
        InitializeResponse response = new InitializeResponse();
        Capabilities capabilities = new Capabilities();
        capabilities.setSupportsBreakpointLocationsRequest(true);
        capabilities.setSupportsExceptionInfoRequest(true);
        response.setBody(capabilities);
        String jsonResponse = gson.toJson(response);
        return jsonResponse;
    }
}