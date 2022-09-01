package src.DebugAdapter;

import com.google.gson.Gson;
import src.DapClasses.Launches.LaunchRequest;
import src.DapClasses.Launches.LaunchResponse;
import src.Emulator.AssemblyEmulator.AssemblyEmulator;

public class LaunchResponseBuilder {
//    public static String processLaunchRequest(Gson gson, String json) throws Exception {
//        LaunchRequest request = gson.fromJson(json, LaunchRequest.class);
//        name = request.getArguments().getName();
//        program = request.getArguments().getProgram();
//        String arr[] = new String[1];
//        arr[0] = program;
//        try {
//            emulator = new AssemblyEmulator(arr);
//            LaunchResponse response = new LaunchResponse();
//            response.setRequest_seq(request.getSeq());
//            response.setSuccess(true);
//            String jsonResponse = gson.toJson(response);
//            return jsonResponse;
//        } catch (Exception e) {
//            processEmulatorException(e);
//        }
//
//        return "";
//    }
}
