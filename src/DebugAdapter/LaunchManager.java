package src.DebugAdapter;

import src.DapClasses.Launches.LaunchRequest;
import src.DapClasses.Launches.LaunchResponse;
import src.Emulator.AssemblyEmulator.AssemblyEmulator;
import com.google.gson.Gson;

import java.io.IOException;

public class LaunchManager {
    private String name;
    private String program;
    private AssemblyEmulator emulator;

    //Creates launch response, if exception occurs sends exceptionInfo response
    private String processLaunchRequest(String json, Gson gson, SendProtocolMessage send) throws IOException {
        ExceptionInfoManager exceptionInfoManager = new ExceptionInfoManager();
        LaunchRequest request = gson.fromJson(json, LaunchRequest.class);
        name = request.getArguments().getName();
        program = request.getArguments().getProgram();
        String arr[] = new String[1];
        arr[0] = program;
        try {
            emulator = new AssemblyEmulator(arr);
            LaunchResponse response = new LaunchResponse();
            response.setRequest_seq(request.getSeq());
            response.setSuccess(true);
            String jsonResponse = gson.toJson(response);
            return jsonResponse;
        } catch (Exception e) {
            exceptionInfoManager.processEmulatorException(e, gson, send);
        }
        return "";
    }

    public AssemblyEmulator getEmulator(){
        return emulator;
    }

    public String getName(){
        return name;
    }

    public String getProgram(){
        return program;
    }

    public String createLaunchResponse(String json, Gson gson, SendProtocolMessage send) throws IOException {
        String LaunchRes = processLaunchRequest(json, gson, send);
        //String LaunchRes = LaunchResponseBuilder.processLaunchRequest(gson, json, name, program);
        send.sendProtocolMessage(LaunchRes);
        return LaunchRes;
    }
}
