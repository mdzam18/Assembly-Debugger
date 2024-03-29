package src.Emulator.AssemblyEmulator;

import src.Emulator.Address.AddressManager;
import src.Emulator.Branches.BranchesManager;
import src.Emulator.Files.FilesManager;
import src.Emulator.Files.Lines;
import src.Emulator.Functions.FunctionsManager;
import src.Emulator.Memory.MemoryManager;
import src.Emulator.Registers.RegistersManager;
import src.Emulator.Stack.StackManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AssemblyEmulator {

    //instance variables
    private RegistersManager registersManager;
    private MemoryManager memoryManager;
    private StackManager stackManager;
    private AddressManager addressManager;
    private BranchesManager branchesManager;
    private FunctionsManager functionsManager;
    private ArrayList<Lines> list;
    private int currentLine;
    private int ending;

    private String assertsText;

    private boolean containsRv;

    public AssemblyEmulator(String[] arr) throws Exception {
        FilesManager files = new FilesManager();
        list = files.createList(arr);
        init();
    }

    //returns result of assert
    public String getAssertsText() {
        return assertsText;
    }

    //If there are several operation on the same Line
    public int getActualLineNumber(){
        return list.get(currentLine).getActualLineNumber();
    }

    public void setCurrentLine(int line) {
        currentLine = line;
    }

    public int getCurrentLine() {
        return currentLine;
    }

    //Returns registers values
    public Map<String, Integer> getRegisters() {
        return registersManager.getRegisters();
    }

    //executes next line
    public boolean next() throws Exception {
        assertsText = "";
        if (currentLine == ending) {
            memoryManager.deleteSavedPC();
            stackManager.removeFunctionName(0);
            currentLine++;
            return false;
        } else {
            int line = processLine(list.get(currentLine).getLine(), currentLine);
            if (line != -1) {
                currentLine = line - 1;
            }
            currentLine++;
            return true;
        }
    }

    public int getSpVirtualValue(){
        //sets virtual value
        int spVirtualValue = 1000000;
        spVirtualValue = spVirtualValue - (memoryManager.getMemoryArraySize() - 1) * 4;
        return spVirtualValue;
    }

    //runs whole code
    public void runWholeCode() throws Exception {
        init();
        while (true) {
            boolean b = next();
            if (!b) {
                break;
            }
        }
    }

    private void init() throws Exception {
        registersManager = new RegistersManager();
        memoryManager = new MemoryManager();
        addressManager = new AddressManager(registersManager, memoryManager);
        stackManager = new StackManager(memoryManager);
        branchesManager = new BranchesManager(registersManager);
        functionsManager = new FunctionsManager(list, memoryManager, stackManager);
        currentLine = functionsManager.getCurrentLine();
        ending = functionsManager.getMainReturnLine();
        assertsText = "";
        containsRv = false;
    }


    //shows stack values of the method on the index in callstack
    public List<String> showStack(int index) throws Exception {
        return stackManager.showStack(index);
    }

    //return true if there is rv in assembly code
    public boolean containsRv(){
        return containsRv;
    }

    public int getRv() {
        return registersManager.getRv();
    }

    public Map<String, Integer> getRegistersWithSP(){
        return registersManager.getRegistersWithSP();
    }

    //returns list of methods in call stack
    public ArrayList<String> getCallStack() {
        return stackManager.getCallStack();
    }

    private void fillRegisters(String line) throws Exception {
        int index = line.indexOf("=");
        String leftSide = line.substring(0, index);
        String str = line.substring(index + 1, line.indexOf(";"));
        int rightSide = addressManager.computeBytes(str);
        if(registersManager.getRegistersWithSP().containsKey(leftSide)){
            registersManager.removeRegister(leftSide);
        }
        if(str.contains("SP") && !str.contains("M")){
            int spVirtualValue = 1000000;
            spVirtualValue = spVirtualValue - 4 * (memoryManager.getMemoryArraySize() - 1);
            registersManager.addRegisterInRegistersWithSP(leftSide, spVirtualValue + rightSide);
        }
        registersManager.addRegister(leftSide, rightSide);
    }

    private int processLine(String line, int numberOfLine) throws Exception {
        if (!line.equals("")) {
            line = line.toUpperCase(Locale.ROOT);
            String left = addressManager.getLeftSide(line);
            if (left.startsWith("R") && !left.startsWith("RET") && !left.startsWith("RV")) {
                fillRegisters(line);
            } else if (left.startsWith("S")) {
                memoryManager.allocateMemory(line);
            } else if (line.startsWith("M")) {
                addressManager.fillMemoryArray(Integer.parseInt(left), line);
            } else if (line.startsWith("B")) {
                return branchesManager.branches(line, numberOfLine);
            } else if (line.startsWith("J")) {
                return branchesManager.jump(line, numberOfLine);
            } else if (line.startsWith("FUNCTION")) {
                functionsManager.processFunction(line, numberOfLine);
            } else if (line.startsWith("CALL")) {
                //call
                if (line.contains("<") && line.contains(">")) {
                    functionsManager.processCall(line, numberOfLine);
                    currentLine = functionsManager.getCurrentLine();
                } else if (!line.contains("<") && !line.contains(">")) {
                    int result = addressManager.getAddress(line.substring(4, line.length() - 1));
                    //aq sad unda gadavaxtuno call-it?
                } else {
                    if (!line.contains("<")) {
                        throw new Exception("missing <");
                    } else {
                        throw new Exception("missing >");
                    }
                }
            } else if (line.startsWith("RET")) {
                //RET
                return functionsManager.processReturns(numberOfLine);
            } else if (line.startsWith("RV")) {
                int rv = addressManager.getValueOfTheRightSide(line.substring(3, line.length() - 1));
                registersManager.setRv(rv);
                registersManager.addRegister("RV", rv);
                containsRv = true;
            } else if (line.startsWith("A")) {
                //ASSERTS
                String currentFunction = functionsManager.getCurrentFunction();
                assertsText = branchesManager.asserts(line, currentFunction) + "\n";
            }
        }
        return -1;
    }
}
