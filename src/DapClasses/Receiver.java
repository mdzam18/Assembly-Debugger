package src.DapClasses;

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
import src.DapClasses.Nexts.NextRequest;
import src.DapClasses.Nexts.NextResponse;
import src.DapClasses.Output.OutputEvent;
import src.DapClasses.Pauses.PauseResponse;
import src.DapClasses.Pauses.ProtocolMessage;
import src.DapClasses.RunInTerminal.RunInTerminalResponse;
import src.DapClasses.Scopes.Scope;
import src.DapClasses.Scopes.ScopesRequest;
import src.DapClasses.Scopes.ScopesResponse;
import src.DapClasses.SetBreakpoints.*;
import src.DapClasses.SetVariables.SetVariableResponse;
import src.DapClasses.Sources.Source;
import src.DapClasses.StackFrames.StackFrame;
import src.DapClasses.StackFrames.StackTraceRequest;
import src.DapClasses.StackFrames.StackTraceResponse;
import src.DapClasses.StepIn.StepInResponse;
import src.DapClasses.StepOut.StepOutResponse;
import src.DapClasses.StoppedEvent.StoppedEvent;
import src.DapClasses.Terminate.TerminateResponse;
import src.DapClasses.Threads.ThreadsRequest;
import src.DapClasses.Threads.ThreadsResponse;
import src.DapClasses.Threads.Thread;
import src.DapClasses.Variables.Variable;
import src.DapClasses.Variables.VariablesRequest;
import src.DapClasses.Variables.VariablesResponse;
import src.DebugAdapter.*;
import src.Emulator.AssemblyEmulator.AssemblyEmulator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class Receiver {
    private FileWriter fWriter = new FileWriter(
            "/Users/mariami/Desktop/Assembly-Debugger-1/src/Emulator/Main/testInputFile");

    private Gson gson;
    private String exceptionMessage;
    //private RequestsReader requestsReader;

    private BreakpointLocationsManager breakpointLocationsManager;
    private  ExceptionInfoManager exceptionInfoManager;
    private SendProtocolMessage send;
    private InitializeManager initializeManager;
    private SetBreakpointsManager setBreakpointsManager;
    private SetExceptionBreakpointsManager setExceptionBreakpointsManager;
    private LaunchResponseManager launchResponseManager;

    public Receiver() throws Exception {
        gson = new Gson();
        init();
        //requestsReader = new RequestsReader();
    }

    private void init(){
        breakpointLocationsManager = new BreakpointLocationsManager();
        exceptionInfoManager = new ExceptionInfoManager();
        send = new SendProtocolMessage();
        initializeManager = new InitializeManager();
        setBreakpointsManager = new SetBreakpointsManager();
        setExceptionBreakpointsManager = new SetExceptionBreakpointsManager();
        launchResponseManager = new LaunchResponseManager();
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
            exceptionInfoManager.processEmulatorException(e);
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

    private void showTextInConsole(String text) throws IOException {
        AssemblyEmulator emulator = launchResponseManager.getEmulator();
        OutputEvent oEvent = new OutputEvent();
        oEvent.setVariablesReference(0);
        oEvent.setOutput(text);
        // oEvent.setLine(emulator.getCurrentLine());
        oEvent.setLine(emulator.getActualLineNumber());
        oEvent.setCategory("stdout");
        oEvent.setGroup("end");
        Event e1 = new Event();
        e1.setEvent("output");
        e1.setBody(oEvent);
        send.sendProtocolMessage(gson.toJson(e1));
    }

    public void callEmulatorNextNTimes(int num) throws Exception {
        AssemblyEmulator emulator = launchResponseManager.getEmulator();
        for (int i = 0; i < num; i++) {
            fWriter.write("\n\n\n Next \n\n\n\n");
            fWriter.flush();
            try {
                boolean isProgramRunning = emulator.next();
                if (emulator.getAssertsText().length() != 0) {
                    //print asserts text
                    showTextInConsole(emulator.getAssertsText());
                }
                if (!isProgramRunning) {
                    if (emulator.containsRv()) {
                        showTextInConsole("RV: " + emulator.getRv() + "\n");
                    }
                    Event terminatedEvent = new Event();
                    terminatedEvent.setEvent("terminated");
                    send.sendProtocolMessage(gson.toJson(terminatedEvent));
                    break;
                }
            } catch (Exception e) {
                exceptionInfoManager.processEmulatorException(e);
            }
        }
    }

    public void callEmulatorNextUntilFistBreakPoint() throws Exception {
        AssemblyEmulator emulator = launchResponseManager.getEmulator();
        List<Integer> breakpointLineNumbers = setBreakpointsManager.getBreakpointLineNumbers();
        try {
            while (true) {
                if (breakpointLineNumbers.contains(emulator.getActualLineNumber() + 1)) {
                    break;
                }
                boolean isProgramRunning = emulator.next();
                fWriter.write("\n\n\n Next blaaaa\n\n\n\n");
                for (int i = 0; i < breakpointLineNumbers.size(); i++) {
                    fWriter.write(String.valueOf(breakpointLineNumbers.get(i)));
                    fWriter.write("\n ");
                }
                fWriter.write("\ncurrent line:   ");
                fWriter.write(String.valueOf(emulator.getActualLineNumber()));
                fWriter.write("\n\n\n ");
                fWriter.flush();
                if (emulator.getAssertsText().length() != 0) {
                    //print asserts text
                    showTextInConsole(emulator.getAssertsText());
                }
                if (!isProgramRunning) {
                    if (emulator.containsRv()) {
                        showTextInConsole("RV: " + emulator.getRv() + "\n");
                    }
                    //aq rom morches mtlianad
                }
            }
        } catch (Exception e) {
            exceptionInfoManager.processEmulatorException(e);
        }

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
            case "setFunctionBreakpoints":
                String SetFunctionBreakpointsRes = processSetFunctionBreakpointsRequest(json);
                send.sendProtocolMessage(SetFunctionBreakpointsRes);
                return SetFunctionBreakpointsRes;
            case "configurationDone":
                String ConfigurationDoneRes = processConfigurationDoneRequest(json);
                send.sendProtocolMessage(ConfigurationDoneRes);
                return ConfigurationDoneRes;
            case "launch":
               return launchResponseManager.createLaunchResponse(json, gson);
            case "breakpointLocations":
                String BreakpointLocationsRes = breakpointLocationsManager.processBreakpointLocationsRequest(json);
                send.sendProtocolMessage(BreakpointLocationsRes);
                return BreakpointLocationsRes;
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
                try {
                    String StackTraceRes = processStackTraceRequest(json);
                    if (emulator.getActualLineNumber() == 0) {
                        callEmulatorNextUntilFistBreakPoint();
                    }
                    send.sendProtocolMessage(StackTraceRes);
                    return StackTraceRes;
                } catch (Exception exception) {
                    //  processEmulatorException(exception);
                }
            case "scopes":
                String ScopesRequestRes = processScopesRequest(json);
                send.sendProtocolMessage(ScopesRequestRes);
                return ScopesRequestRes;
            case "variables":
                String VariablesRequestRes = processVariablesRequest(json);
                send.sendProtocolMessage(VariablesRequestRes);
                return VariablesRequestRes;
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
                    callEmulatorNextNTimes(nextBreakpointLine - currLine);
                } else {
                    emulator.runWholeCode();
                }

                return ContinueRequestRes;
            case "next":
                String NextRequestRes = processNextRequest(json);
                send.sendProtocolMessage(NextRequestRes);
                StoppedEvent stoppedEvent1 = new StoppedEvent();
                stoppedEvent1.setReason("step");
                stoppedEvent1.setThreadId(1);
                Event e1 = new Event();
                e1.setBody(stoppedEvent1);
                e1.setEvent("stopped");
                send.sendProtocolMessage(gson.toJson(e1));
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
                send.sendProtocolMessage(DisconnectRequestRes);
                return DisconnectRequestRes;
            case "exceptionInfo":
                String exceptionInfoRequestRes = exceptionInfoManager.processExceptionInfoRequest(json, exceptionMessage);
               send.sendProtocolMessage(exceptionInfoRequestRes);
                return exceptionInfoRequestRes;
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

    private Variable[] showRegisters() {
        AssemblyEmulator emulator = launchResponseManager.getEmulator();
        Map<String, Integer> variablesMap = emulator.getRegisters();
        Map<String, Integer> registersWithSp = emulator.getRegistersWithSP();
        Variable[] variables = new Variable[variablesMap.size()];
        int counter = 0;
        for (String key : variablesMap.keySet()) {
            if(!key.equals("RV")) {
                Variable v = new Variable();
                v.setName(key);
                v.setVariablesReference(0);
                if(registersWithSp.containsKey(key)){
                    v.setValue(String.valueOf(registersWithSp.get(key)));
                } else {
                    v.setValue(String.valueOf(variablesMap.get(key)));
                }
                variables[counter] = v;
                counter++;
            }
        }
        return variables;
    }

    //returns rv and virtual value of sp
    private Variable[] showSpecialRegisters(){
        AssemblyEmulator emulator = launchResponseManager.getEmulator();
        if(emulator.containsRv()) {
            Variable[] variables = new Variable[2];
            Variable v = new Variable();
            v.setName("RV");
            v.setVariablesReference(0);
            v.setValue(String.valueOf(emulator.getRv()));
            variables[0] = v;

            v = new Variable();
            v.setName("Virtual address of SP");
            v.setVariablesReference(0);
            v.setValue(String.valueOf(emulator.getSpVirtualValue()));
            variables[1] = v;
            return variables;
        }
        Variable[] variables = new Variable[1];
        Variable v = new Variable();

        v = new Variable();
        v.setName("Virtual address of SP");
        v.setVariablesReference(0);
        v.setValue(String.valueOf(emulator.getSpVirtualValue()));
        variables[0] = v;
        return variables;
    }

    //shows stack frame of every method in callstack
    private Variable[] showStackFrame() throws Exception {
        AssemblyEmulator emulator = launchResponseManager.getEmulator();
        List<String> callStack = emulator.getCallStack();
        Variable[] variables = new Variable[callStack.size()];
        for (int j = 0; j < callStack.size(); j++) {
            List<String> stackFrame = emulator.showStack(j);
            Variable v = new Variable();
            v.setName(callStack.get(j));
            v.setVariablesReference(0);
            String[] arr = new String[stackFrame.size()];
            for (int i = 0; i < stackFrame.size(); i++) {
                arr[i] = stackFrame.get(i);
            }
            v.setValue(Arrays.toString(arr));
            variables[j] = v;
        }
        return variables;
    }

    private String processVariablesRequest(String json) throws Exception {
        VariablesRequest request = gson.fromJson(json, VariablesRequest.class);
        VariablesResponse response = new VariablesResponse();
        Variable[] variables = null;
        if (request.getArguments().getVariablesReference() == 10) {
            //registers
            variables = showRegisters();
        } else if(request.getArguments().getVariablesReference() == 11){
            variables = showStackFrame();
        } else {
            variables = showSpecialRegisters();
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
        Scope[] scopes = new Scope[3];
        scopes[0] = new Scope();
        scopes[0].setExpensive(false);
        scopes[0].setName("Registers");
        scopes[0].setVariablesReference(10);
        scopes[1] = new Scope();
        scopes[1].setExpensive(false);
        scopes[1].setName("Stack Frame");
        scopes[1].setVariablesReference(11);
        scopes[2] = new Scope();
        scopes[2].setExpensive(false);
        scopes[2].setName("Special Registers");
        scopes[2].setVariablesReference(12);
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
        AssemblyEmulator emulator = launchResponseManager.getEmulator();
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
        List<Integer> breakpointLineNumbers = setBreakpointsManager.getBreakpointLineNumbers();
        for (int i = 0; i < callstack.size(); i++) {
            stackFrames[i] = new StackFrame();
            stackFrames[i].setColumn(0);
            stackFrames[i].setId(0);
            if (emulator.getActualLineNumber() == 0) {
                stackFrames[i].setLine(breakpointLineNumbers.get(0));
            } else {
                stackFrames[i].setLine(emulator.getActualLineNumber() + 1);
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
        source.setName(launchResponseManager.getName());
        source.setAdapterData("mock-adapter-data");
        source.setSourceReference(0);
        source.setPath(launchResponseManager.getProgram());
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

    private String processConfigurationDoneRequest(String json) {
        ConfigurationDoneRequest request = gson.fromJson(json, ConfigurationDoneRequest.class);
        ConfigurationDoneResponse response = new ConfigurationDoneResponse();
        response.setRequest_seq(request.getSeq());
        response.setSuccess(true);
        String jsonResponse = gson.toJson(response);
        return jsonResponse;
    }

}
