package src.Emulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FunctionsManager {

    private Map<String, Integer> functions;
    private Map<Integer, String> returns;
    private ArrayList<Integer> returnsIndexes;
    private ArrayList<String> list;
    private RegistersManager registersManager;
    private MemoryManager memoryManager;
    private StackManager stackManager;
    private String currentFunction;
    private int currentLine;

    public FunctionsManager(ArrayList<String> list, RegistersManager registersManager, MemoryManager memoryManager, StackManager stackManager){
        functions = new HashMap<>();
        returns = new HashMap<>();
        returnsIndexes = new ArrayList<>();
        this.list = list;
        this.registersManager = registersManager;
        this.memoryManager = memoryManager;
        this.stackManager = stackManager;
        fillFunctionArray();
        fillReturnsIndex();
        currentFunction = "FUNCTIONMAIN";
        currentLine = functions.get("FUNCTIONMAIN");
    }

    private void fillReturnsIndex() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).startsWith("FUNCTION")) {
                getAllReturns(i + 1, list.get(i).substring(0, list.get(i).length() - 1));
            }
        }
    }

    private void getAllReturns(int index, String name) {
        for (int i = index; i < list.size(); i++) {
            if (list.get(i).startsWith("FUNCTION")) {
                break;
            }
            if (list.get(i).startsWith("RET")) {
                returns.put(i, name);
            }
        }
    }

    public void processCall(String line, int numberOfLine) {
        int length = memoryManager.getMemory().length;
        line = line.substring(line.indexOf('<') + 1, line.indexOf(('>')));
        memoryManager.resizeMemory(length + 1); //add saved pc
        String str = "FUNCTION" + line;
        stackManager.addFunctionName(line); //add function name
        stackManager.addSavedPc(length - 1);
        callFunction(str);
        returnsIndexes.add(numberOfLine);
    }

    public int processReturns(int numberOfLine) {
        int length = memoryManager.getMemory().length;
        if (returnsIndexes.size() == 0) {
            return list.size() - 1;
        }
        memoryManager.resizeMemory(length - 1); //delete saved pc
        int ret = returnsIndexes.get(returnsIndexes.size() - 1);
        returnsIndexes.remove(returnsIndexes.size() - 1);
        int index = 0;
        ArrayList<String> callStack = stackManager.getCallStack();
        ArrayList<Integer> savedPc = stackManager.getSavedPc();
        for (int i = callStack.size() - 1; i >= 0; i--) {
            if (callStack.get(i).equals(returns.get(numberOfLine).substring(8))) {
                index = i;
                break;
            }
        }
        callStack.remove(index);
        savedPc.remove(index);
        return ret + 1;
    }

    private void fillFunctionArray(){
        for(int i = 0; i < list.size(); i++){
            String line = list.get(i);
            if (line.startsWith("FUNCTION")) {
                String str = line.substring(0, line.length() - 1);
                functions.put(str, i);
            }
        }
    }

    public String getCurrentFunction(){
        return currentFunction;
    }

    private void callFunction(String name) {
        int index = functions.get(name) + 1;
        String curr = name.substring(8);
        if (curr.startsWith("TEST")) {
            currentFunction = name;
        }
        currentLine = index - 1;
    }

    public int getCurrentLine(){
        return currentLine;
    }

    public void processFunction(String line, int numberOfLine) {
        line = line.substring(8, line.length() - 1);
        functions.put(line, numberOfLine);
        int index = getReturnIndex(numberOfLine);
        returns.put(index, line);
    }

    private int getReturnIndex(int index) {
        for (int i = index; i < list.size(); i++) {
            if (list.get(i).startsWith("RET")) {
                return i;
            }
        }
        return -1;
    }
}
