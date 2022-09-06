package src.DebugAdapter;

import com.google.gson.Gson;
import src.DapClasses.Pauses.PauseResponse;
import src.DapClasses.Pauses.ProtocolMessage;
import src.DapClasses.Request;
import src.DapClasses.RunInTerminal.RunInTerminalResponse;
import src.Emulator.AssemblyEmulator.AssemblyEmulator;

import java.io.FileWriter;
import java.util.*;


public class Receiver {
    private FileWriter fWriter = new FileWriter(
            "/home/nroga/Final/Assembly-Debugger/src/Emulator/Main/testInputFile");

    private Gson gson;
    private BreakpointLocationsManager breakpointLocationsManager;
    private  ExceptionInfoManager exceptionInfoManager;
    private SendProtocolMessage send;
    private InitializeManager initializeManager;
    private SetBreakpointsManager setBreakpointsManager;
    private LaunchManager launchResponseManager;
    private StackTraceManager stackTraceManager;
    private ScopesManager scopesManager;
    private VariablesManager variablesManager;
    private NextManager nextManager;
    private DisconnectManager disconnectManager;
    private ContinueManager continueManager;
    private ThreadsManager threadsManager;
    private  ConfigurationDoneManager configurationDoneManager;

    public Receiver() throws Exception {
        init();
    }

    private void init(){
        gson = new Gson();
        breakpointLocationsManager = new BreakpointLocationsManager();
        exceptionInfoManager = new ExceptionInfoManager();
        send = new SendProtocolMessage();
        initializeManager = new InitializeManager();
        setBreakpointsManager = new SetBreakpointsManager();
        launchResponseManager = new LaunchManager();
        stackTraceManager = new StackTraceManager();
        scopesManager = new ScopesManager();
        variablesManager = new VariablesManager();
        nextManager = new NextManager();
        disconnectManager = new DisconnectManager();
        continueManager = new ContinueManager();
        threadsManager = new ThreadsManager();
        configurationDoneManager = new ConfigurationDoneManager();
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
                return "";
        }
        return null;
    }

    private String processRequest(String json) throws Exception {
        Request request = gson.fromJson(json, Request.class);
        String command = request.getCommand();
        AssemblyEmulator emulator = launchResponseManager.getEmulator();
        switch (command) {
            case "initialize":
                return initializeManager.createInitializeResponse(request, json, gson, send);
            case "setBreakpoints":
                return setBreakpointsManager.createSetBreakpointResponse(json, gson, send);
            case "configurationDone":
                return configurationDoneManager.createConfigurationDoneResponse(json, send, gson);
            case "launch":
               return launchResponseManager.createLaunchResponse(json, gson, send);
            case "breakpointLocations":
                return breakpointLocationsManager.createBreakpointResponse(json, gson, send);
            case "runInTerminal":
                return processRunInTerminalRequest();
            case "threads":
                return threadsManager.createThreadsResponse(json, gson, send);
            case "stackTrace":
                return stackTraceManager.createStackTraceResponse(send, exceptionInfoManager, launchResponseManager.getName(), launchResponseManager.getProgram(), setBreakpointsManager.getBreakpointLineNumbers(), emulator, gson, json);
            case "scopes":
                return scopesManager.createScopesResponse(json, gson, send);
            case "variables":
                return variablesManager.createVariablesResponse(json, gson, emulator, send);
            case "pause":
                return processPauseRequest();
            case "continue":
                return continueManager.createContinueResponse(exceptionInfoManager, json, send, gson, emulator, setBreakpointsManager.getBreakpointLineNumbers());
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

    private String processPauseRequest() {
        PauseResponse response = new PauseResponse();
        return null;
    }

    private String processRunInTerminalRequest() {
        RunInTerminalResponse response = new RunInTerminalResponse();
        return null;
    }

}
