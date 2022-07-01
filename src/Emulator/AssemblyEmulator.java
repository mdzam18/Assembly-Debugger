package src.Emulator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

public class AssemblyEmulator {

    private RegistersManager registersManager;
    private MemoryManager memoryManager;
    private StackManager stackManager;
    private AddressManager addressManager;
    private BranchesManager branchesManager;
    private FunctionsManager functionsManager;

    private ArrayList<String> list;
    private int currentLine;

    public AssemblyEmulator(FileReader file) throws IOException {
        list = new ArrayList<>();
        readFile(file);
        init();
        currentLine = functionsManager.getCurrentLine();
    }

    public Map<String, Integer> getRegisters(){
        return registersManager.getRegisters();
    }

    public boolean next() throws Exception {
        //list.size() -1 imitom rom boloshi sruldeba ret-it.
        if (currentLine == (list.size() - 1)) {
            memoryManager.deleteSavedPC();
            currentLine++;
            return false;
        } else {
            //    System.out.println(currentLine + 1);
            int line = processLine(list.get(currentLine), currentLine);
            if (line != -1) {
                currentLine = line - 1;
            }
            currentLine++;
            return true;
        }
    }

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


    private void init() {
        registersManager = new RegistersManager();
        memoryManager = new MemoryManager();
        addressManager = new AddressManager(registersManager, memoryManager);
        stackManager = new StackManager();
        branchesManager = new BranchesManager(registersManager, memoryManager);
        functionsManager = new FunctionsManager(list, registersManager, memoryManager, stackManager);
    }

    public void showStack(String functionName) {
        stackManager.showStack(functionName);
    }

    public ArrayList<String> getCallStack() {
        return stackManager.getCallStack();
    }

    private void fillRegisters(String line) throws Exception {
        int index = line.indexOf("=");
        String leftSide = line.substring(0, index);
        String str = line.substring(index + 1, line.indexOf(";"));
        int rightSide = addressManager.computeBytes(str);
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
            } else if (line.startsWith("FUNCTION") && !line.startsWith("FUNCTIONMAIN")) {
                functionsManager.processFunction(line, numberOfLine);
            } else if (line.startsWith("CALL")) {
                //call
                if (line.contains("<")) {
                    functionsManager.processCall(line, numberOfLine);
                    currentLine = functionsManager.getCurrentLine();
                } else {
                    int result = addressManager.getAddress(line.substring(4, line.length() - 1));
                    //aq sad unda gadavaxtuno call-it?
                }
            } else if (line.startsWith("RET")) {
                //RET
                return functionsManager.processReturns(numberOfLine);
            } else if (line.startsWith("RV")) {
                int rv = addressManager.getValueOfTheRightSide(line.substring(3, line.length() - 1));
                registersManager.setRv(rv);
                System.out.println("RV " + rv);
            } else if (line.startsWith("A")) {
                //ASSERTS
                String currentFunction = functionsManager.getCurrentFunction();
                branchesManager.asserts(line, currentFunction);
            }
        }
        return -1;
    }

    private void readFile(FileReader file) throws IOException {
        BufferedReader rd = new BufferedReader(file);
        while (true) {
            String line = rd.readLine();
            if (line == null) {
                break;
            }
            line = line.replaceAll(" ", ""); //Delete white spaces.
            line = line.toUpperCase(Locale.ROOT);
            list.add(line);
        }
        rd.close();
    }

}
