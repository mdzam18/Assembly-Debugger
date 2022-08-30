package src.Emulator.AssemblyEmulator;

import src.Emulator.Address.AddressManager;
import src.Emulator.Branches.BranchesManager;
import src.Emulator.Files.FilesManager;
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
    private ArrayList<String> list;
    private int currentLine;

    public AssemblyEmulator(String[] arr) throws Exception {
        FilesManager files = new FilesManager();
        list = files.createList(arr);
        init();
        currentLine = functionsManager.getCurrentLine();
    }

    public void setCurrentLine(int line){
        currentLine = line;
    }
    public int getCurrentLine(){
        return currentLine;
    }

    //Returns registers values
    public Map<String, Integer> getRegisters() {
        return registersManager.getRegisters();
    }

    //executes next line
    public boolean next() throws Exception {
        if (currentLine == (list.size())) {
            if (!list.get(currentLine - 1).startsWith("RET")){
                throw new Exception("missing RET");
            }
            memoryManager.deleteSavedPC();
            currentLine++;
            return false;
        } else {
            int line = processLine(list.get(currentLine), currentLine);
            if (line != -1) {
                currentLine = line - 1;
            }
            currentLine++;
            return true;
        }
    }

    //debugs whole code
    public void debug() throws Exception {
        init();
        while (true) {
            boolean b = next();
            if (!b) {
                break;
            }
        }
        printRegisters();
        printMemory();
        System.out.println("RV: " + registersManager.getRv());
    }

    private void printRegisters() {
        Map<String, Integer> registers = registersManager.getRegisters();
        for (String r : registers.keySet()) {
            System.out.println(r + " : " + registers.get(r));
        }
    }

    private void printMemory() {
        int[] memory = memoryManager.getMemory();
        for (int j : memory) {
            System.out.println(j);
        }
    }


    private void init() throws Exception {
        registersManager = new RegistersManager();
        memoryManager = new MemoryManager();
        addressManager = new AddressManager(registersManager, memoryManager);
        stackManager = new StackManager(memoryManager);
        branchesManager = new BranchesManager(registersManager);
        functionsManager = new FunctionsManager(list, memoryManager, stackManager);
    }


    //shows stack values
    public List<String> showStack(String functionName) throws Exception {
        return stackManager.showStack(functionName);
    }

    public int getRv(){
        return registersManager.getRv();
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
        System.out.println(leftSide + " " + rightSide + " current");
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
                    if(!line.contains("<")){
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
            } else if (line.startsWith("A")) {
                //ASSERTS
                String currentFunction = functionsManager.getCurrentFunction();
                branchesManager.asserts(line, currentFunction);
            }
        }
        return -1;
    }
}
