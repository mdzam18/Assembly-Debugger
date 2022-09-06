package src.DebugAdapter;

import com.google.gson.Gson;
import src.DapClasses.Configurations.ConfigurationDoneRequest;
import src.DapClasses.Configurations.ConfigurationDoneResponse;

public class ConfigurationDoneManager {
    public static String processConfigurationDoneRequest(Gson gson, String json) {
        ConfigurationDoneRequest request = gson.fromJson(json, ConfigurationDoneRequest.class);
        ConfigurationDoneResponse response = new ConfigurationDoneResponse();
        response.setRequest_seq(request.getSeq());
        response.setSuccess(true);
        String jsonResponse = gson.toJson(response);
        return jsonResponse;
    }
}
