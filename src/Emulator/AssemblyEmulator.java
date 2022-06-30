package src.Emulator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AssemblyEmulator {

    private RegistersManager registersManager;
    private MemoryManager memoryManager;
    private StackManager stackManager;
    private AddressManager addressManager;
    private BranchesManager branchesManager;

    private Map<String, Integer> functions;
    private Map<Integer, String> returns;
    private ArrayList<String> list;
    private int currentLine;
    private String currentFunction;
    private ArrayList<Integer> returnsIndexes;

    public AssemblyEmulator(FileReader file) throws IOException {
        init();
        readFile(file);
        fillReturnsIndex();
        currentLine = functions.get("FUNCTIONMAIN");
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

    private void initAgain() {
        registersManager = new RegistersManager();
        memoryManager = new MemoryManager();
        stackManager = new StackManager();
        addressManager = new AddressManager(registersManager, memoryManager);
        branchesManager = new BranchesManager(registersManager, memoryManager);

        returns = new HashMap<>();
        currentFunction = "FUNCTIONMAIN";
        fillReturnsIndex();
        currentLine = functions.get("FUNCTIONMAIN");
        returnsIndexes = new ArrayList<>();
    }

    public void debug() throws Exception {
        initAgain();
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

    private void fillReturnsIndex() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).startsWith("FUNCTION")) {
                getAllReturns(i + 1, list.get(i).substring(0, list.get(i).length() - 1));
            }
        }
       /* for (int ret : returns.keySet()) {
            System.out.println("key: " + ret + " val: " + returns.get(ret));
        } */
    }

    private void getAllReturns(int index, String name) {
        // System.out.println(index);
        for (int i = index; i < list.size(); i++) {
            if (list.get(i).startsWith("FUNCTION")) {
                break;
            }
            if (list.get(i).startsWith("RET")) {
                returns.put(i, name);
            }
        }
    }

    private void init() {
        registersManager = new RegistersManager();
        memoryManager = new MemoryManager();
        addressManager = new AddressManager(registersManager, memoryManager);
        stackManager = new StackManager();
        branchesManager = new BranchesManager(registersManager, memoryManager);

        returns = new HashMap<>();
        list = new ArrayList<>();
        functions = new HashMap<>();
        currentFunction = "FUNCTIONMAIN";
        returnsIndexes = new ArrayList<>();
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
            } else if (line.startsWith("FUNCTION") && numberOfLine != functions.get("FUNCTIONMAIN")) {
                processFunction(line, numberOfLine);
            } else if (line.startsWith("CALL")) {
                //call
                if (line.contains("<")) {
                    processCall(line, numberOfLine);
                } else {
                    int result = addressManager.getAddress(line.substring(4, line.length() - 1));
                    //aq sad unda gadavaxtuno call-it?
                }
            } else if (line.startsWith("RET")) {
                //RET
                return processReturns(numberOfLine);
            } else if (line.startsWith("RV")) {
                int rv = addressManager.getValueOfTheRightSide(line.substring(3, line.length() - 1));
                registersManager.setRv(rv);
                System.out.println("RV " + rv);
            } else if (line.startsWith("A")) {
                //ASSERTS
                branchesManager.asserts(line, currentFunction);
            }
        }
        return -1;
    }


    private void processFunction(String line, int numberOfLine) {
        line = line.substring(8, line.length() - 1);
        functions.put(line, numberOfLine);
        int index = getReturnIndex(numberOfLine);
        returns.put(index, line);
    }

    private void processCall(String line, int numberOfLine) {
        int length = memoryManager.getMemory().length;
        line = line.substring(line.indexOf('<') + 1, line.indexOf(('>')));
        memoryManager.resizeMemory(length + 1); //add saved pc
        String str = "FUNCTION" + line;
        stackManager.addFunctionName(line); //add function name
        stackManager.addSavedPc(length - 1);
        callFunction(str);
        returnsIndexes.add(numberOfLine);
    }

    private int processReturns(int numberOfLine) {
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

    private void callFunction(String name) {
        int index = functions.get(name) + 1;
        String curr = name.substring(8);
        if (curr.startsWith("TEST")) {
            currentFunction = name;
        }
        currentLine = index - 1;
    }

    private int getReturnIndex(int index) {
        for (int i = index; i < list.size(); i++) {
            if (list.get(i).startsWith("RET")) {
                return i;
            }
        }
        return -1;
    }

    private int compareValues(String type, int n, int m, int pc) {
        boolean isTrue = false;
        switch (type) {
            case "LT":
                if (n < m) isTrue = true;
                break;
            case "LE":
                if (n <= m) isTrue = true;
                break;
            case "EQ":
                if (n == m) isTrue = true;
                break;
            case "NE":
                if (n != m) isTrue = true;
                break;
            case "GT":
                if (n > m) isTrue = true;
                break;
            case "GE":
                if (n >= m) isTrue = true;
                break;
        }
        if (isTrue) return pc;
        return -1;
    }

    private void readFile(FileReader file) throws IOException {
        BufferedReader rd = new BufferedReader(file);
        int number = 0;
        while (true) {
            String line = rd.readLine();
            if (line == null) {
                break;
            }
            line = line.replaceAll(" ", ""); //Delete white spaces.
            line = line.toUpperCase(Locale.ROOT);
            if (line.startsWith("FUNCTION")) {
                String str = line.substring(0, line.length() - 1);
                functions.put(str, number);
            }
            list.add(line);
            number++;
        }
        rd.close();
    }

}
