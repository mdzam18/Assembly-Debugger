package src.DebugAdapter;

import com.google.gson.Gson;
import src.DapClasses.Attachs.AttachResponse;
import src.DapClasses.Configurations.ConfigurationDoneRequest;
import src.DapClasses.Configurations.ConfigurationDoneResponse;
import src.DapClasses.Continues.ContinueRequest;
import src.DapClasses.Continues.ContinueResponse;
import src.DapClasses.Continues.ContinuedEvent;
import src.DapClasses.Disconnects.DisconnectRequest;
import src.DapClasses.Disconnects.DisconnectResponse;
import src.DapClasses.Event.Event;
import src.DapClasses.Pauses.PauseResponse;
import src.DapClasses.Pauses.ProtocolMessage;
import src.DapClasses.Request;
import src.DapClasses.Response;
import src.DapClasses.RunInTerminal.RunInTerminalResponse;
import src.DapClasses.SetVariables.SetVariableResponse;
import src.DapClasses.StoppedEvent.StoppedEvent;
import src.DapClasses.Threads.Thread;
import src.DapClasses.Threads.ThreadsRequest;
import src.DapClasses.Threads.ThreadsResponse;
import src.Emulator.AssemblyEmulator.AssemblyEmulator;

import java.io.FileWriter;
import java.util.*;


public class Receiver {
    private FileWriter fWriter = new FileWriter(
            "/Users/mariami/Desktop/Assembly-Debugger-1/src/Emulator/Main/testInputFile");

    private Gson gson;
    //private RequestsReader requestsReader;
    private BreakpointLocationsManager breakpointLocationsManager;
    private  ExceptionInfoManager exceptionInfoManager;
    private SendProtocolMessage send;
    private InitializeManager initializeManager;
    private SetBreakpointsManager setBreakpointsManager;
    private SetExceptionBreakpointsManager setExceptionBreakpointsManager;
    private LaunchResponseManager launchResponseManager;
    private StackTraceManager stackTraceManager;
    private CallEmulatorMethods callEmulatorMethods;
    private ScopesManager scopesManager;
    private VariablesManager variablesManager;
    private NextManager nextManager;
    private DisconnectManager disconnectManager;

    public Receiver() throws Exception {
        init();
        //requestsReader = new RequestsReader();
    }

    private void init(){
        gson = new Gson();
        breakpointLocationsManager = new BreakpointLocationsManager();
        exceptionInfoManager = new ExceptionInfoManager();
        send = new SendProtocolMessage();
        initializeManager = new InitializeManager();
        setBreakpointsManager = new SetBreakpointsManager();
        setExceptionBreakpointsManager = new SetExceptionBreakpointsManager();
        launchResponseManager = new LaunchResponseManager();
        stackTraceManager = new StackTraceManager();
        callEmulatorMethods = new CallEmulatorMethods();
        scopesManager = new ScopesManager();
        variablesManager = new VariablesManager();
        nextManager = new NextManager();
        disconnectManager = new DisconnectManager();
    }

    private String readHeader(Scanner scanner) {
        String current = "";
        while (true) {
            String s = "";
            while (true) {
                String s2 = scanner.next();
                if (s2.startsWith("\r")) {
                    break;
                }
                s = s + s2;
            }
            current = current + s;
            String next = scanner.next();
            if (next.startsWith("\n")) {
                break;
            } else {
                current = current + "\r";
            }
        }
        return current;
    }

    private String readRequest(Scanner scanner) {
        Map<String, String> headers = new HashMap<>();
        while (true) {
            String header = readHeader(scanner);
            String[] items = header.split(": ", 2);
            if (items.length != 2) {
                break;
            }
            headers.put(items[0], items[1]);
        }
        int length = Integer.parseInt(headers.get("Content-Length"));
        StringBuilder rem = new StringBuilder();
        for (int i = 0; i < length; i++) {
            rem.append(scanner.next());
        }
        return rem.toString();
    }

    public void receive() throws Exception {
        Scanner scanner = null;
        try {
            scanner = new Scanner(System.in);
            scanner.useDelimiter("");
            while (true) {
                String message = readRequest(scanner);
                //String message = requestsReader.readRequest();
                fWriter.write("\n Received \n\n");
                fWriter.write(message);
                fWriter.flush();
                receiveProtocolMessage(message);
            }
        } catch (Exception e) {
            exceptionInfoManager.processEmulatorException(e, gson, send);
        } finally {
            fWriter.close();
        }
    }

    private String receiveProtocolMessage(String json) throws Exception {
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

    private String processRequest(String json) throws Exception {
        Request request = gson.fromJson(json, Request.class);
        String command = request.getCommand();
        AssemblyEmulator emulator = launchResponseManager.getEmulator();
        switch (command) {
            case "initialize":
                return initializeManager.createInitializeResponse(request, json);
            case "setBreakpoints":
                return setBreakpointsManager.createSetBreakpointResponse(json);
            case "setExceptionBreakpoints":
                return setExceptionBreakpointsManager.processSetExceptionBreakpointsRequest(gson, json);
            case "configurationDone":
                String ConfigurationDoneRes = processConfigurationDoneRequest(json);
                send.sendProtocolMessage(ConfigurationDoneRes);
                return ConfigurationDoneRes;
            case "launch":
               return launchResponseManager.createLaunchResponse(json, gson, send);
            case "breakpointLocations":
                return breakpointLocationsManager.createBreakpointResponse(json, gson);
            case "runInTerminal":
                return processRunInTerminalRequest();
            case "attach":
                return processSetVariableRequest();
            case "setVariable":
                return processAttachRequest();
            case "threads":
                String ThreadsRes = processThreadsRequest(json);
                send.sendProtocolMessage(ThreadsRes);
                return ThreadsRes;
            case "stackTrace":
                return stackTraceManager.createStackTraceResponse(exceptionInfoManager, launchResponseManager.getName(), launchResponseManager.getProgram(), setBreakpointsManager.getBreakpointLineNumbers(), emulator, gson, json);
            case "scopes":
                return scopesManager.createScopesResponse(json, gson);
            case "variables":
                return variablesManager.createVariablesResponse(json, gson, emulator, send);
            case "pause":
                return processPauseRequest();
            case "continue":
                String ContinueRequestRes = processContinueRequest(json);
                send.sendProtocolMessage(ContinueRequestRes);
                ContinuedEvent ce = new ContinuedEvent();
                ce.setThreadId(1);
                ce.setAllThreadsContinued(true);
                Event x = new Event();
                x.setEvent("continued");
                x.setBody(ce);
               send.sendProtocolMessage(gson.toJson(x));
                int currLine = emulator.getActualLineNumber() + 1;
                int nextBreakpointLine = -1;
                List<Integer> breakpointLineNumbers = setBreakpointsManager.getBreakpointLineNumbers();
                for (int i = 0; i < breakpointLineNumbers.size(); i++) {
                    if (currLine < breakpointLineNumbers.get(i)) {
                        nextBreakpointLine = breakpointLineNumbers.get(i);
                        break;
                    }
                }
                if (nextBreakpointLine != -1) {
                    callEmulatorMethods.callEmulatorNextNTimes(nextBreakpointLine - currLine, emulator, gson, exceptionInfoManager);
                } else {
                    emulator.runWholeCode();
                }
                return ContinueRequestRes;
            case "next":
                return nextManager.createNextResponse(exceptionInfoManager, json, gson, send, emulator);
            case "disconnect":
                return disconnectManager.createDisconnectResponse(json, gson, send);
            case "exceptionInfo":
                exceptionInfoManager.createExceptionInfoResponse(json, gson, send);
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

    private String processDisconnectRequest(String json) {
        DisconnectRequest request = gson.fromJson(json, DisconnectRequest.class);
        DisconnectResponse response = new DisconnectResponse();
        response.setRequest_seq(request.getSeq());
        response.setSuccess(true);
        return gson.toJson(response);
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

    private String processAttachRequest() {
        AttachResponse response = new AttachResponse();
        return null;
    }

    private String processRunInTerminalRequest() {
        RunInTerminalResponse response = new RunInTerminalResponse();
        return null;
    }

    private String processConfigurationDoneRequest(String json) {
        ConfigurationDoneRequest request = gson.fromJson(json, ConfigurationDoneRequest.class);
        ConfigurationDoneResponse response = new ConfigurationDoneResponse();
        response.setRequest_seq(request.getSeq());
        response.setSuccess(true);
        String jsonResponse = gson.toJson(response);
        return jsonResponse;
    }

}
