package src.Emulator.Functions;

import src.Emulator.Files.Lines;
import src.Emulator.Memory.MemoryManager;
import src.Emulator.Stack.StackManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FunctionsManager {

    //instance variables
    private Map<String, Integer> functions;
    private Map<Integer, String> returns;
    private ArrayList<Integer> returnsIndexes;
    private ArrayList<Lines> list;
    private MemoryManager memoryManager;
    private StackManager stackManager;
    private String currentFunction;
    private int currentLine;
    private int retOfMain;

    public int getMainReturnLine(){
        return retOfMain;
    }

    public FunctionsManager(ArrayList<Lines> list, MemoryManager memoryManager, StackManager stackManager) throws Exception {
        functions = new HashMap<>();
        returns = new HashMap<>();
        returnsIndexes = new ArrayList<>();
        this.list = list;
        this.memoryManager = memoryManager;
        this.stackManager = stackManager;
        fillFunctionArray();
        fillReturnsIndex();
        //starting function
        currentFunction = "FUNCTIONMAIN";
        currentLine = functions.get("FUNCTIONMAIN");
    }

    private void fillReturnsIndex() throws Exception {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getLine().startsWith("FUNCTION")) {
                getAllReturns(i + 1, list.get(i).getLine().substring(0, list.get(i).getLine().length() - 1));
            }
        }
    }

    private void getAllReturns(int index, String name) throws Exception {
        boolean been = false;
        for (int i = index; i < list.size(); i++) {
            if (list.get(i).getLine().startsWith("FUNCTION")) {
                break;
            }
            if (list.get(i).getLine().startsWith("RET")) {
                returns.put(i, name);
                been = true;
                if(name.equals("FUNCTIONMAIN")){
                    retOfMain = i;
                }
            }
        }
        if (!been) {
            throw new Exception("missing RET of: " + name.substring(8));
        }
    }

    //process call function, jumps to the function and adds names to callstack
    public void processCall(String line, int numberOfLine) throws Exception {
        int length = memoryManager.getMemory().length;
        line = line.substring(line.indexOf('<') + 1, line.indexOf(('>')));
        memoryManager.resizeMemory(length + 1); //add saved pc
        String str = "FUNCTION" + line;
        stackManager.addFunctionName(line); //add function name
        stackManager.addSavedPc(length);
        callFunction(str);
        returnsIndexes.add(numberOfLine);
    }

    //Finds line where to go after ret
    public int processReturns(int numberOfLine) {
        int length = memoryManager.getMemory().length;
        if (returnsIndexes.size() == 0) {
            stackManager.removeFunctionName(0); //remove MAIN
            return list.size();
        }
        memoryManager.resizeMemory(length - 1); //delete saved pc
        int ret = returnsIndexes.get(returnsIndexes.size() - 1);
        returnsIndexes.remove(returnsIndexes.size() - 1);
        int index = 0;
        ArrayList<String> callStack = stackManager.getCallStack();
        if (returns.get(numberOfLine).equals("FUNCTIONMAIN")) {
            return -1;
        }
        for (int i = callStack.size() - 1; i >= 0; i--) {
            if (callStack.get(i).equals(returns.get(numberOfLine).substring(8))) {
                index = i;
                break;
            }
        }
        stackManager.removeFunctionName(index);
        stackManager.removeSavedPc(index);
//        if (returns.get(numberOfLine).startsWith("MAIN")) {
//            return -1;
//        }
        return ret + 1;
    }

    private void fillFunctionArray() {
        for (int i = 0; i < list.size(); i++) {
            String line = list.get(i).getLine();
            if (line.startsWith("FUNCTION")) {
                String str = line.substring(0, line.length() - 1);
                functions.put(str, i);
            }
        }
    }

    public String getCurrentFunction() {
        return currentFunction;
    }

    private void callFunction(String name) throws Exception {
        if(!functions.containsKey(name)){
            throw new Exception("function " + name.substring(8) + " does not exist.");
        }
        int index = functions.get(name) + 1;
        String curr = name.substring(8);
        if (curr.startsWith("TEST")) {
            currentFunction = name;
        }
        currentLine = index - 1;
    }

    public int getCurrentLine() {
        return currentLine;
    }

    //fills functions map
    public Map<String, Integer> processFunction(String line, int numberOfLine) throws Exception {
        line = line.substring(0, line.length() - 1);
        functions.put(line, numberOfLine);
        int index = getReturnIndex(numberOfLine);
        returns.put(index, line);
        return functions;
    }

    //returns the index of the first ret found after this index
    private int getReturnIndex(int index) throws Exception {
        for (int i = index + 1; i < list.size(); i++) {
            if (list.get(i).getLine().startsWith("RET")) {
                return i;
            }
            if (list.get(i).getLine().startsWith("FUNCTION")) {
                break;
            }
        }
        throw new Exception("missing RET of : " + list.get(index));
    }
}
