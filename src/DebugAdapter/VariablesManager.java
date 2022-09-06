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

public class VariablesManager {
    private String processVariablesRequest(String json, Gson gson, AssemblyEmulator emulator) throws Exception {
        VariablesRequest request = gson.fromJson(json, VariablesRequest.class);
        VariablesResponse response = new VariablesResponse();
        Variable[] variables = null;
        if (request.getArguments().getVariablesReference() == 10) {
            //registers
            variables = showRegisters(emulator);
        } else if(request.getArguments().getVariablesReference() == 11){
            variables = showStackFrame(emulator);
        } else {
            variables = showSpecialRegisters(emulator);
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

    //Shows all the registers used in the code
    private Variable[] showRegisters(AssemblyEmulator emulator) {
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

    //shows stack frame of every method in callstack
    private Variable[] showStackFrame(AssemblyEmulator emulator) throws Exception {
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

    //returns rv and virtual value of sp
    private Variable[] showSpecialRegisters(AssemblyEmulator emulator){
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

    public String createVariablesResponse(String json, Gson gson, AssemblyEmulator emulator, SendProtocolMessage send) throws Exception {
        String VariablesRequestRes = processVariablesRequest(json, gson, emulator);
        send.sendProtocolMessage(VariablesRequestRes);
        return VariablesRequestRes;
    }
}
