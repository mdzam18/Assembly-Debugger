//package src.DapClasses;
//
//import src.Emulator.AssemblyEmulator.AssemblyEmulator;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//
//public class ResponseCreator {
////    private String processExceptionInfoRequest(String json) throws IOException {
////        ExceptionInfoRequest request = gson.fromJson(json, ExceptionInfoRequest.class);
////        Response r = new Response();
////        r.setCommand("exceptionInfo");
////        r.setRequest_seq(request.getSeq());
////        r.setSuccess(true);
////        ExceptionInfoResponse response = new ExceptionInfoResponse();
////        response.setExceptionId("1");
////        response.setDescription(exceptionMessage);
////        response.setBreakMode("always");
////        r.setBody(response);
////        String jsonResponse = gson.toJson(r);
////        return jsonResponse;
////    }
////
////    private String processExitedEvent() {
////        return null;
////    }
////
////    private String processTerminatedEvent(String json) {
////        return null;
////    }
////
////    private String processThreadEvent() {
////        return null;
////    }
////
////    private String processStoppedEvent(String json) {
////        StoppedEvent request = gson.fromJson(json, StoppedEvent.class);
////        return null;
////    }
////
////    private Variable[] showRegisters() {
////        Map<String, Integer> variablesMap = emulator.getRegisters();
////        Variable[] variables = new Variable[variablesMap.size()];
////        int counter = 0;
////        for (String key : variablesMap.keySet()) {
////            Variable v = new Variable();
////            v.setName(key);
////            v.setVariablesReference(0);
////            v.setValue(String.valueOf(variablesMap.get(key)));
////            variables[counter] = v;
////            counter++;
////        }
////        return variables;
////    }
////
////    //shows stack frame of every method in callstack
////    private Variable[] showStackFrame() throws Exception {
////        List<String> callStack = emulator.getCallStack();
////        Variable[] variables = new Variable[callStack.size()];
////        for (int j = 0; j < callStack.size(); j++) {
////            List<String> stackFrame = emulator.showStack(callStack.get(j));
////            Variable v = new Variable();
////            v.setName(callStack.get(j));
////            v.setVariablesReference(0);
////            String[] arr = new String[stackFrame.size()];
////            for (int i = 0; i < stackFrame.size(); i++) {
////                arr[i] = stackFrame.get(i);
////            }
////            v.setValue(Arrays.toString(arr));
////            variables[j] = v;
////        }
////        return variables;
////    }
////
////    private String processVariablesRequest(String json) throws Exception {
////        VariablesRequest request = gson.fromJson(json, VariablesRequest.class);
////        VariablesResponse response = new VariablesResponse();
////        Variable[] variables = null;
////        if (request.getArguments().getVariablesReference() == 10) {
////            //registers
////            variables = showRegisters();
////        } else {
////            variables = showStackFrame();
////        }
////        response.setVariables(variables);
////        Response r = new Response();
////        r.setCommand("variables");
////        r.setRequest_seq(request.getSeq());
////        r.setSuccess(true);
////        r.setBody(response);
////        String jsonResponse = gson.toJson(r);
////        return jsonResponse;
////    }
////
////    private String processScopesRequest(String json) {
////        ScopesRequest request = gson.fromJson(json, ScopesRequest.class);
////        ScopesResponse response = new ScopesResponse();
////        Scope[] scopes = new Scope[2];
////        scopes[0] = new Scope();
////        scopes[0].setExpensive(false);
////        scopes[0].setName("Registers");
////        scopes[0].setVariablesReference(10);
////        scopes[1] = new Scope();
////        scopes[1].setExpensive(false);
////        scopes[1].setName("Stack Frame");
////        scopes[1].setVariablesReference(11);
////        response.setScopes(scopes);
////        Response r = new Response();
////        r.setCommand("scopes");
////        r.setRequest_seq(request.getSeq());
////        r.setSuccess(true);
////        r.setBody(response);
////        String jsonResponse = gson.toJson(r);
////        return jsonResponse;
////    }
////
////    private String processDisconnectRequest(String json) {
////        DisconnectRequest request = gson.fromJson(json, DisconnectRequest.class);
////        DisconnectResponse response = new DisconnectResponse();
////        response.setRequest_seq(request.getSeq());
////        response.setSuccess(true);
////        return gson.toJson(response);
////    }
////
////    private String processTerminateRequest() {
////        TerminateResponse response = new TerminateResponse();
////        return null;
////    }
////
////    private String processStepOutRequest() {
////        StepOutResponse response = new StepOutResponse();
////        return null;
////    }
////
////    private String processStepInRequest() {
////        StepInResponse response = new StepInResponse();
////        return null;
////    }
////
////    private String processNextRequest(String json) {
////        NextRequest request = gson.fromJson(json, NextRequest.class);
////        NextResponse response = new NextResponse();
////        response.setRequest_seq(request.getSeq());
////        response.setSuccess(true);
////        String jsonResponse = gson.toJson(response);
////        return jsonResponse;
////    }
////
////    private String processContinueRequest(String json) {
////        ContinueRequest request = gson.fromJson(json, ContinueRequest.class);
////        ContinueResponse response = new ContinueResponse();
////        Response r = new Response();
////        r.setCommand("continue");
////        r.setRequest_seq(request.getSeq());
////        r.setSuccess(true);
////        response.setAllThreadsContinued(true);
////        // r.setBody(response);
////        String jsonResponse = gson.toJson(r);
////        return jsonResponse;
////    }
////
////    private String processPauseRequest() {
////        PauseResponse response = new PauseResponse();
////        return null;
////    }
////
////    private String processStackTraceRequest(String json) {
////        StackTraceRequest request = gson.fromJson(json, StackTraceRequest.class);
////        Response r = new Response();
////        r.setSuccess(true);
////        r.setRequest_seq(request.getSeq());
////        r.setCommand("stackTrace");
////        StackTraceResponse response = new StackTraceResponse();
////        ArrayList<String> callstack = emulator.getCallStack();
////        //if(callstack.size() == 0){
////        //  processTerminateRequest();
////        //anu morcha da unda shevawyvetino
////        // }
////        StackFrame[] stackFrames = new StackFrame[callstack.size()];
////        for (int i = 0; i < callstack.size(); i++) {
////            stackFrames[i] = new StackFrame();
////            stackFrames[i].setColumn(0);
////            stackFrames[i].setId(0);
////            if (emulator.getCurrentLine() == 0) {
////                stackFrames[i].setLine(breakpointLineNumbers.get(0));
////            } else {
////                stackFrames[i].setLine(emulator.getCurrentLine() + 1);
////            }
////            stackFrames[i].setName(callstack.get(i));
////            stackFrames[i].setSource(createSource());
////        }
////        response.setStackFrames(stackFrames);
////        response.setTotalFrames(response.getStackFrames().length);
////        r.setBody(response);
////        String jsonResponse = gson.toJson(r);
////        return jsonResponse;
////    }
////
////    private Source createSource() {
////        Source source = new Source();
////        source.setName(name);
////        source.setAdapterData("mock-adapter-data");
////        source.setSourceReference(0);
////        source.setPath(program);
////        return source;
////    }
////
////    private String processThreadsRequest(String json) {
////        ThreadsRequest request = gson.fromJson(json, ThreadsRequest.class);
////        ThreadsResponse response = new ThreadsResponse();
////        Thread[] threads = new Thread[1];
////        threads[0] = new Thread();
////        threads[0].setId(1);
////        threads[0].setName("thread 1");
////        response.setThreads(threads);
////        Response r = new Response();
////        r.setBody(response);
////        r.setCommand("threads");
////        r.setRequest_seq(request.getSeq());
////        r.setSuccess(true);
////        String jsonResponse = gson.toJson(r);
////        return jsonResponse;
////    }
////
////    private String processSetVariableRequest() {
////        SetVariableResponse response = new SetVariableResponse();
////        return null;
////    }
////
////    private String processSetFunctionBreakpointsRequest(String json) {
////        SetFunctionBreakpointsRequest request = gson.fromJson(json, SetFunctionBreakpointsRequest.class);
////        SetFunctionBreakpointsResponse response = new SetFunctionBreakpointsResponse();
////        response.setRequest_seq(request.getSeq());
////        response.setSuccess(true);
////        String jsonResponse = gson.toJson(response);
////        return jsonResponse;
////    }
////
////    private String processAttachRequest() {
////        AttachResponse response = new AttachResponse();
////        return null;
////    }
////
////    private String processRunInTerminalRequest() {
////        RunInTerminalResponse response = new RunInTerminalResponse();
////        return null;
////    }
////
////    private String processBreakpointLocationsRequest(String json) {
////        BreakpointLocationsRequest request = gson.fromJson(json, BreakpointLocationsRequest.class);
////        BreakpointLocationsResponse response = new BreakpointLocationsResponse();
////        response.setRequest_seq(request.getSeq());
////        response.setSuccess(true);
////        BreakpointLocation[] breakpointLocations = new BreakpointLocation[1];
////        breakpointLocations[0] = new BreakpointLocation();
////        breakpointLocations[0].setLine(request.getArguments().getLine());
////        breakpointLocations[0].setEndLine(request.getArguments().getEndLine());
////        breakpointLocations[0].setColumn(request.getArguments().getColumn());
////        breakpointLocations[0].setEndColumn(request.getArguments().getEndColumn());
////        response.setBody(breakpointLocations);
////        String jsonResponse = gson.toJson(response);
////        return jsonResponse;
////    }
////
////    private String processLaunchRequest(String json) throws Exception {
////        LaunchRequest request = gson.fromJson(json, LaunchRequest.class);
////        name = request.getArguments().getName();
////        program = request.getArguments().getProgram();
////        String arr[] = new String[1];
////        arr[0] = program;
////        try {
////            emulator = new AssemblyEmulator(arr);
////            LaunchResponse response = new LaunchResponse();
////            response.setRequest_seq(request.getSeq());
////            response.setSuccess(true);
////            String jsonResponse = gson.toJson(response);
////            return jsonResponse;
////        } catch (Exception e) {
////            processEmulatorException(e);
////        }
////
////        return "";
////    }
////
////    private String processConfigurationDoneRequest(String json) {
////        ConfigurationDoneRequest request = gson.fromJson(json, ConfigurationDoneRequest.class);
////        ConfigurationDoneResponse response = new ConfigurationDoneResponse();
////        response.setRequest_seq(request.getSeq());
////        response.setSuccess(true);
////        String jsonResponse = gson.toJson(response);
////        return jsonResponse;
////    }
////
////    private String processSetExceptionBreakpointsRequest(String json) {
////        SetExceptionBreakpointsRequest request = gson.fromJson(json, SetExceptionBreakpointsRequest.class);
////        SetExceptionBreakpointsResponse response = new SetExceptionBreakpointsResponse();
////        String jsonResponse = gson.toJson(response);
////        return jsonResponse;
////    }
////
////    public String processSetBreakpointsRequest(String json) throws IOException {
////        SetBreakpointsRequest request = gson.fromJson(json, SetBreakpointsRequest.class);
////        SetBreakpointsResponse response = new SetBreakpointsResponse();
////        response.setRequest_seq(request.getSeq());
////        response.setSuccess(true);
////        SourceBreakpoint[] requestBreakpoints = request.getArguments().getBreakpoints();
////        int[] requestLines = request.getArguments().getLines();
////        for (int i = 0; i < requestLines.length; i++) {
////            breakpointLineNumbers.add(requestLines[i]);
////        }
////        breakpoints = new Breakpoint[requestBreakpoints.length];
////        for (int i = 0; i < requestBreakpoints.length; i++) {
////            breakpoints[i] = new Breakpoint();
////            breakpoints[i].setLine(requestBreakpoints[i].getLine());
////            //breakpointLineNumbers.add(requestBreakpoints[i].getLine());
////            breakpoints[i].setVerified(true);
////        }
////        response.setBody(breakpoints);
////        String jsonResponse = gson.toJson(response);
////        return jsonResponse;
////    }
////
////    public String processInitializeRequest(String json) {
////        InitializeRequest request = gson.fromJson(json, InitializeRequest.class);
////        InitializeResponse response = new InitializeResponse();
////        Capabilities capabilities = new Capabilities();
////        capabilities.setSupportsBreakpointLocationsRequest(true);
////        capabilities.setSupportsExceptionInfoRequest(true);
////        response.setBody(capabilities);
////        String jsonResponse = gson.toJson(response);
////        return jsonResponse;
////    }
//}
