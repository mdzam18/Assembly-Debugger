package src.DebugAdapter;

import com.google.gson.Gson;
import src.DapClasses.Response;
import src.DapClasses.Variables.Variable;
import src.DapClasses.Variables.VariablesRequest;
import src.DapClasses.Variables.VariablesResponse;
import src.Emulator.AssemblyEmulator.AssemblyEmulator;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class VariablesResponseBuilder {
    public static String processVariablesRequest(AssemblyEmulator emulator, Gson gson, String json) throws Exception {
        VariablesRequest request = gson.fromJson(json, VariablesRequest.class);
        VariablesResponse response = new VariablesResponse();
        Variable[] variables = null;
        if (request.getArguments().getVariablesReference() == 10) {
            //registers
            variables = showRegisters(emulator);
        } else {
            variables = showStackFrame(emulator);
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
    private static Variable[] showRegisters(AssemblyEmulator emulator) {
        Map<String, Integer> variablesMap = emulator.getRegisters();
        Variable[] variables = new Variable[variablesMap.size()];
        int counter = 0;
        for (String key : variablesMap.keySet()) {
            Variable v = new Variable();
            v.setName(key);
            v.setVariablesReference(0);
            v.setValue(String.valueOf(variablesMap.get(key)));
            variables[counter] = v;
            counter++;
        }
        return variables;
    }
    private static Variable[] showStackFrame(AssemblyEmulator emulator) throws Exception {
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
}
