import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AssemblyEmulator {

    private Map<String, Integer> registers;
    private Map<String, Integer> functions;
    private Map<Integer, String> returns;
    private Map<String, Integer> functionCalls;
    private ArrayList<String> list;
    private int[] memory;
    private int rv;
    private int currentLine;
    private String currentFunction;
    private ArrayList<String> callStack;
    private ArrayList<Integer> savedPc;
    private ArrayList<Integer> returnsIndexes;

    public AssemblyEmulator(FileReader file) throws IOException {
        init();
        readFile(file);
        fillReturnsIndex();
        currentLine = functions.get("FUNCTIONMAIN");
    }

    public Map<String, Integer> getRegisters() {
        return registers;
    }

    public boolean next() throws Exception {
        //list.size() -1 imitom rom boloshi sruldeba ret-it.
        if (currentLine == (list.size() - 1)) {
            deleteSavedPC();
            currentLine++;
            return false;
        } else {
            System.out.println(currentLine + 1);
            int line = processLine(list.get(currentLine), currentLine);
            if (line != -1) {
                currentLine = line - 1;
            }
            currentLine++;
            return true;
        }
    }

    public void showStack(String functionName) {
        int index = 0;
        for (int i = callStack.size() - 1; i >= 0; i--) {
            if (callStack.get(i).equals(functionName.toUpperCase(Locale.ROOT))) {
                index = i;
                break;
            }
        }
        int lastIndex = memory.length;
        if (savedPc.size() > (index + 1)) {
            lastIndex = savedPc.get(index + 1);
        }
        System.out.println("SAVED PC");
        for (int i = savedPc.get(index) + 1; i < lastIndex; i++) {
            System.out.println(memory[i]);
        }
    }

    private void initAgain() {
        returns = new HashMap<>();
        registers = new HashMap<>();
        functionCalls = new HashMap<>();
        memory = new int[1]; //saved pc
        callStack = new ArrayList<>();
        callStack.add("MAIN");
        savedPc = new ArrayList<>();
        savedPc.add(0);
        currentFunction = "FUNCTIONMAIN";
        returns.put(list.size() - 1, "FUNCTIONMAIN");
        functionCalls.put(currentFunction, currentLine);
        fillReturnsIndex();
        currentLine = functions.get("FUNCTIONMAIN");
        returnsIndexes = new ArrayList<>();
    }

    public void debug() throws Exception {
        initAgain();
        while (true) {
            //  getCallStack();
            boolean b = next();
            if (!b) {
                break;
            }
        }
        printMap();
        printMemory();
        System.out.println("RV: " + rv);
    }

    private void fillReturnsIndex() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).startsWith("FUNCTION")) {
                getAllReturns(i + 1, list.get(i).substring(0, list.get(i).length() - 1));
            }
        }
        for (int ret : returns.keySet()) {
            System.out.println("key: " + ret + " val: " + returns.get(ret));
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

    private void init() {
        returns = new HashMap<>();
        list = new ArrayList<>();
        registers = new HashMap<>();
        functions = new HashMap<>();
        functionCalls = new HashMap<>();
        memory = new int[1]; //saved pc
        callStack = new ArrayList<>();
        callStack.add("MAIN");
        savedPc = new ArrayList<>();
        savedPc.add(0);
        currentFunction = "FUNCTIONMAIN";
        returns.put(list.size() - 1, "FUNCTIONMAIN");
        functionCalls.put(currentFunction, currentLine);
        returnsIndexes = new ArrayList<>();
    }

    private void printMemory() {
        for (int i = 0; i < memory.length; i++) {
            System.out.println(i + " " + memory[i]);
        }
    }

    public ArrayList<String> getCallStack() {
        System.out.println("CALL STACK");
        for (int i = 0; i < callStack.size(); i++) {
            System.out.println(callStack.get(i));
        }
        return callStack;
    }

    private int processLine(String line, int numberOfLine) throws Exception {
        // printMemory();
        if (!line.equals("")) {
            line = line.toUpperCase(Locale.ROOT);
            String left = getLeftSide(line);
            if (left.startsWith("R") && !left.startsWith("RET") && !left.startsWith("RV")) {
                fillRegistersMaps(line);
            } else if (left.startsWith("S")) {
                allocateMemory(line);
            } else if (line.startsWith("M")) {
                fillMemoryArray(Integer.parseInt(left), line);
            } else if (line.startsWith("B")) {
                return branches(line, numberOfLine);
            } else if (line.startsWith("J")) {
                return jump(line, numberOfLine);
            } else if (line.startsWith("FUNCTION") && numberOfLine != functions.get("FUNCTIONMAIN")) {
                line = line.substring(8, line.length() - 1);
                functions.put(line, numberOfLine);
                int index = getReturnIndex(numberOfLine);
                returns.put(index, line);
            } else if (line.startsWith("CALL")) {
                //call
                if (line.contains("<")) {
                    line = line.substring(line.indexOf('<') + 1, line.indexOf(('>')));
                    resizeMemory(memory.length + 1); //add saved pc
                    String str = "FUNCTION" + line;
                    callStack.add(line); //add function name
                    functionCalls.put(str, numberOfLine);
                    savedPc.add(memory.length - 1);
                    callFunction(str);
                    returnsIndexes.add(numberOfLine);
                } else {
                    int result = getAddress(line.substring(4, line.length() - 1));
                    //memory[(int)result]
                }
            } else if (line.startsWith("RET")) {
                //RET
                return processReturns(numberOfLine);
            } else if (line.startsWith("RV")) {
                rv = getValueOfTheRightSide(line.substring(3, line.length() - 1));
                System.out.println("RV " + rv);
            } else if (line.startsWith("A")) {
                //ASSERTS
                asserts(line);
            }
        }
        return -1;
    }

    private int processReturns(int numberOfLine){
        resizeMemory(memory.length - 1); //delete saved pc
        int ret = returnsIndexes.get(returnsIndexes.size() - 1);
        returnsIndexes.remove(returnsIndexes.size() - 1);
        int index = 0;
        for (int i = callStack.size() - 1; i >= 0; i++) {
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

    private int jump(String str, int numberOfLine) throws Exception {
        String s = str.substring(3, str.length() - 1);
        int pc = getValue(s, numberOfLine);
        return pc + 1;
    }

    private void asserts(String str) throws Exception {
        String type = str.substring(1, 3);
        int index = str.indexOf(',');
        int a = getValue(str.substring(3, index), 0);
        int b = getValue(str.substring(index + 1, str.length() - 1), 0);
        int result = compareValues(type, a, b, 0);
        //  System.out.println(str + " " + type + " " + a + " " + b);
        if (result == 0) {
            //assert
            int expected = a;
            int got = b;
            if (str.substring(3, index).equals("RV")) {
                expected = b;
                got = a;
            }
            System.out.println(currentFunction.substring(8) + " " + "FAILED");
            System.out.println("Expected " + expected + " Got " + got);
        } else {
            System.out.println(currentFunction.substring(8) + " " + "PASSED");
        }
    }

    private int branches(String str, int numberOfLine) throws Exception {
        String type = str.substring(1, 3);
        int index = str.indexOf(',');
        int a = getValue(str.substring(3, index), numberOfLine);
        int b = getValue(str.substring(index + 1, str.indexOf(',', index + 1)), numberOfLine);
        int pc = getValue(str.substring(str.indexOf(',', index + 1) + 1, str.indexOf(";")), numberOfLine);
        return compareValues(type, a, b, pc);
    }

    private int compareValues(String type, int n, int m, int pc) {
        boolean isTrue = false;
        if (type.equals("LT")) {
            if (n < m) isTrue = true;
        } else if (type.equals("LE")) {
            if (n <= m) isTrue = true;
        } else if (type.equals("EQ")) {
            if (n == m) isTrue = true;
        } else if (type.equals("NE")) {
            if (n != m) isTrue = true;
        } else if (type.equals("GT")) {
            if (n > m) isTrue = true;
        } else if (type.equals("GE")) {
            if (n >= m) isTrue = true;
        }
        if (isTrue) return pc;
        return -1;
    }

    private int getValue(String str, int numberOfLine) throws Exception {
        //M[sp] ეგეთი ოპერაცია არ მოსულაო, ეწერა.
        if (str.startsWith("RV")) {
            return rv;
        }
        if (str.startsWith("R") && !str.startsWith("RET") && !str.startsWith("RV")) {
            if (registers.containsKey(str)) {
                return registers.get(str);
            } else {
                throw new Exception("invalid register");
            }
        } else if (isNumber(str)) {
            return Integer.parseInt(str);
        } else {
            if (containsOperator(str)) {
                char operator = getOperator(str);
                int number = Integer.parseInt(str.substring(str.indexOf(operator) + 1)) / 4;
                if (operator == '-') {
                    return numberOfLine - number;
                } else {
                    return numberOfLine + number;
                }
            } else {
                return numberOfLine;
            }
        }
    }

    private void fillMemoryArray(int location, String line) throws Exception {
        if (location >= memory.length) {
            System.out.println("location " + location + " " + "memory length " + memory.length);
            throw new Exception("out of bounds.");
        } else {
            String str = line.substring(line.indexOf("=") + 1, line.indexOf(";"));
            int res = computeBytes(str);
            memory[location] = res;
        }
    }

    private int computeBytes(String str) throws Exception {
        char c = '0';
        boolean toInt = false;
        if (str.startsWith(".")) {
            c = str.charAt(1);
            str = str.substring(2);
        } else if (str.startsWith("FTOI")) {
            toInt = true;
            str = str.substring(4);
        }
        int res = getValueOfTheRightSide(str);
        if (toInt) {
            int number = res;
            res = number;
        }
        if (c != '0') {
            int n = c - '0';
            n = 32 - n * 8;
            //   System.out.println(res); //double-is saxit cota sxvanairi ricxvi iwereba da iyos ase?
            res = res << n;
            res = res >> n;
        }
        return res;
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

    private void printMap() {
        for (String key : registers.keySet()) {
            System.out.println(key + " " + registers.get(key));
        }
    }

    private void fillRegistersMaps(String line) throws Exception {
        int index = line.indexOf("=");
        String leftSide = line.substring(0, index);
        String str = line.substring(index + 1, line.indexOf(";"));
        int rightSide = computeBytes(str);
        registers.put(leftSide, rightSide);
    }

    private int getAddress(String str) throws Exception {
        if (isNumber(str)) {
            return Integer.parseInt(str) / 4; //zustad ar vici es
        } else if (containsOperator(str)) {
            //an sp + a an a + sp an a + r an r + 1 an r1 + r2
            return computeResult(str) / 4;
        } else {
            if (str.startsWith("R") && !str.startsWith("RET") && !str.startsWith("RV")) {
                if (registers.containsKey(str)) {
                    return registers.get(str) / 4;
                } else {
                    throw new Exception("invalid register");
                }
            } else {
                return memory.length - 1;
            }
        }
    }

    private int getValueOfTheRightSide(String str) throws Exception {
        if (isNumber(str)) { //loads constants.
            return Integer.parseInt(str);
        } else if (str.charAt(0) == '-' && isNumber(str.substring(1))) { //is negative number.
            return -1 * Integer.parseInt(str.substring(1));
        } else if (containsOperator(str) && !str.startsWith("M")) {
            //an sp + a an a + sp an a + r an r + 1 an r1 + r2
            return computeResult(str);
        } else if (str.startsWith("M")) {
            int res = getAddress(str.substring(str.indexOf('[') + 1, str.indexOf(']')));
            if (res >= memory.length) {
                throw new Exception("invalid address");
            }
            return memory[res];
        } else if (str.startsWith("RV")) {
            return rv;
        } else {
            if (str.startsWith("R") && !str.startsWith("RET")) {
                if (!registers.containsKey(str)) {
                    throw new Exception("invalid register");
                }
                return registers.get(str);
            } else {
                System.out.println("sasuke");
                return (memory.length - 1) * 4;
            }
        }
    }

    private int computeResult(String str) {
        char operator = getOperator(str);
        int index = str.indexOf(operator);
        String first = str.substring(0, index);
        String second = str.substring(index + 1);
        if (first.startsWith("RV")) {
            first = Integer.toString(rv);
        }
        if (second.startsWith("RV")) {
            second = Integer.toString(rv);
        }
        if (isNumber(first)) {
            return computeValue(first, second, operator, true);
        } else if (isNumber(second)) {
            return computeValue(second, first, operator, false);
        } else if (isNumber(first) && isNumber(second)) {
            return Integer.parseInt(first) + Integer.parseInt(second);
        } else {
            int value1 = registers.get(first);
            int value2 = registers.get(second);
            return compute(value1, value2, operator);
        }
    }

    private int computeValue(String first, String second, char operator, boolean isOrdered) {
        if (second.startsWith("S")) {
            return findMemoryArrayIndex(operator, Integer.parseInt(first)) * 4;
        } else {
            //starts with R.
            int val = registers.get(second);
            if (isOrdered) {
                return compute(Integer.parseInt(first), val, operator);
            } else {
                return compute(val, Integer.parseInt(first), operator);
            }
        }
    }

    private int findMemoryArrayIndex(char operator, int number) {
        if (operator == '+') {
            return memory.length - number / 4 - 1;
        } else {
            return memory.length + number / 4 - 1;
        }
    }

    private void allocateMemory(String line) {
        int index = line.indexOf("=");
        int size = computeMemorySize(line.substring(index + 1, line.indexOf(";")));
        resizeMemory(size);
    }

    private void deleteSavedPC() {
        int[] curr = new int[memory.length - 1];
        for (int i = 1; i < memory.length; i++) {
            curr[i - 1] = memory[i];
        }
        memory = curr;
    }

    private void resizeMemory(int size) {
        // System.out.println("size " + size);
        int[] curr = new int[size];
        for (int i = 0; i < Math.min(size, memory.length); i++) {
            curr[i] = memory[i];
        }
        memory = curr;
    }

    private int computeMemorySize(String str) {
        if (str.contains("-")) {
            int index = str.indexOf("-");
            int size = Integer.parseInt(str.substring(index + 1)) / 4;
            return memory.length + size;
        } else {
            int index = str.indexOf("+");
            return memory.length - Integer.parseInt(str.substring(index + 1)) / 4;
        }
    }

    private String getLeftSide(String str) throws Exception {
        if (str.startsWith("RET")) {
            return "RET";
        } else if (str.startsWith("R")) {
            return str.substring(0, str.indexOf("="));
        } else if (str.startsWith("M")) {
            String address = str.substring(str.indexOf("[") + 1, str.indexOf("]"));
            int res = getAddress(address);
            return Integer.toString(res);
        } else if (str.startsWith("S")) {
            return str.substring(0, str.indexOf("="));
        } else {
            return "";
        }
    }

    private boolean containsOperator(String address) {
        return address.indexOf('+') != -1 || address.indexOf('-') != -1 || address.indexOf('*') != -1 || address.indexOf('/') != -1;
    }

    private int compute(int a, int b, char operator) {
        if (operator == '+') {
            return a + b;
        } else if (operator == '-') {
            return a - b;
        } else if (operator == '*') {
            return a * b;
        } else {
            return a / b;
        }
    }

    private char getOperator(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '+' || str.charAt(i) == '-' || str.charAt(i) == '*' || str.charAt(i) == '/') {
                return str.charAt(i);
            }
        }
        return 0;
    }

    private boolean isNumber(String address) {
        for (int i = 0; i < address.length(); i++) {
            if (!Character.isDigit(address.charAt(i)) && address.charAt(i) != '.') {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) throws Exception {
        String fileName = "\\Users\\mdzam\\Desktop\\assembly\\Assembly-Debugger\\Emulator\\src\\calltests.txt";
        // String fileName = args[0];
        AssemblyEmulator emulator = new AssemblyEmulator(new FileReader(fileName));
        emulator.debug();
//        emulator.next();
//        emulator.next();
//        emulator.next();
//        emulator.next();
//        emulator.next();
//        emulator.next();
//        emulator.next();
//        emulator.next();
//        emulator.next();
//        emulator.next();
//        emulator.next();
//        emulator.next();
//        emulator.next();
//        emulator.next();
        emulator.getCallStack();
        emulator.showStack("fact");
    }
}
