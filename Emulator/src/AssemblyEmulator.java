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

    public AssemblyEmulator(FileReader file) throws IOException {
        init();
        readFile(file);
        int start = functions.get("FUNCTIONMAIN");
        fillReturnsIndex();
        //list.size() -1 imitom rom boloshi sruldeba ret-it.
        for (int i = start + 1; i < list.size(); i++) {
            System.out.println("i " + i);
            if(i == (list.size() - 1)){
               deleteSavedPC();
            } else {
                int line = processLine(list.get(i), i);
                if (line != -1) {
                    i = line - 1;
                }
            }
        };
        printMap();
        printMemory();
        System.out.println("RV: " + rv);
    }



    private void fillReturnsIndex(){
        for(int i = 0 ; i < list.size(); i++){
            if(list.get(i).startsWith("FUNCTION")){
                getAllReturns(i+1, list.get(i).substring(0, list.get(i).length() - 1));
            }
        }
    }

    private void getAllReturns(int index, String name){
        for(int i = index; i < list.size(); i++){
            if(list.get(i).startsWith("FUNCTION")){
                break;
            }
            if(list.get(i).startsWith("RET")){
                returns.put(i, name);
            }
        }
    }

    private void init(){
        returns = new HashMap<>();
        list = new ArrayList<>();
        registers = new HashMap<>();
        functions = new HashMap<>();
        functionCalls = new HashMap<>();
        memory = new int[1]; //saved pc
    }

    private void printMemory() {
        for (int i = 0; i < memory.length; i++) {
            System.out.println(i + " " + memory[i]);
        }
    }

    private int processLine(String line, int numberOfLine) {
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
            } else if(line.startsWith("FUNCTION") && numberOfLine != functions.get("FUNCTIONMAIN")){
                line = line.substring(8, line.length() - 1);
                functions.put(line, numberOfLine);
                int index = getReturnIndex(numberOfLine);
                returns.put(index, line);
            } else if(line.startsWith("CALL")){
                //call
                if(line.contains("<")) {
                    line = line.substring(line.indexOf('<') + 1, line.indexOf(('>')));
                    resizeMemory(memory.length + 1); //add saved pc
                    String str = "FUNCTION" + line;
                    functionCalls.put(str, numberOfLine);
                    callFunction(str);
                    resizeMemory(memory.length - 1); //delete saved pc
                } else {
                    int result = getAddress(line.substring(4, line.length() - 1));
                    //memory[(int)result]
                }
            } else if(line.startsWith("RET")){
                //RET
                return functionCalls.get(returns.get(numberOfLine)) + 1;
            } else if(line.startsWith("RV")){
                rv = getValueOfTheRightSide(line.substring(3, line.length() - 1));
                System.out.println("RV " + rv);
            }
        }
        return -1;
    }

    private void callFunction(String name){
        int index = functions.get(name) + 1;
        for(int i = index; i < list.size(); i++){
            if(list.get(i).startsWith("RET")){
                break;
            }
            int x = processLine(list.get(i), i);
            if (x != -1) {
                i = x - 1;
            }
        }
    }

    private int getReturnIndex(int index){
        for(int i = index; i < list.size(); i++){
            if(list.get(i).startsWith("RET")){
                return i;
            }
        }
        return -1;
    }

    private int jump(String str, int numberOfLine){
        String s = str.substring(3, str.length() - 1);
        int pc = (int) getValue(s, numberOfLine);
        return pc + 1;
    }

    private int branches(String str, int numberOfLine) {
        String type = str.substring(0, 3);
        int index = str.indexOf(',');
        int a = getValue(str.substring(3, index), numberOfLine);
        int b = getValue(str.substring(index + 1, str.indexOf(',', index + 1)), numberOfLine);
        int pc = (int) getValue(str.substring(str.indexOf(',', index + 1) + 1, str.indexOf(";")), numberOfLine);
        return compareValues(type, a, b, pc);
    }

    private int compareValues(String type, int n, int m, int pc) {
        boolean isTrue = false;
        if (type.equals("BLT")) {
            if (n < m) isTrue = true;
        } else if (type.equals("BLE")) {
            if (n <= m) isTrue = true;
        } else if (type.equals("BEQ")) {
            if (n == m) isTrue = true;
        } else if (type.equals("BNE")) {
            if (n != m) isTrue = true;
        } else if (type.equals("BGT")) {
            if (n > m) isTrue = true;
        } else if (type.equals("BGE")) {
            if (n >= m) isTrue = true;
        }
        if (isTrue) return pc;
        return -1;
    }

    private int getValue(String str, int numberOfLine) {
        //M[sp] ეგეთი ოპერაცია არ მოსულაო, ეწერა.
        if(str.startsWith("RV")){
            return rv;
        }
        if (str.startsWith("R") && !str.startsWith("RET") && !str.startsWith("RV")) {
            return registers.get(str);
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

    private void fillMemoryArray(int location, String line) {
        if (location >= memory.length) {
            System.out.println(location + " " + memory.length);
            System.out.println("error");
        } else {
            String str = line.substring(line.indexOf("=") + 1, line.indexOf(";"));
            int res = computeBytes(str);
            memory[location] = res;
        }
    }

    private int computeBytes(String str) {
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
            int number = (int) res;
            res = number;
        }
        if (c != '0') {
            int n = c - '0';
            n = 32 - n * 8;
         //   System.out.println(res); //double-is saxit cota sxvanairi ricxvi iwereba da iyos ase?
            res = (int) res << n; //es vikitxo romel mxares aris sachiro gaweva.
            res = (int) res >> n;
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
            if(line.startsWith("FUNCTION")){
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

    private void fillRegistersMaps(String line) {
        int index = line.indexOf("=");
        String leftSide = line.substring(0, index);
        String str = line.substring(index + 1, line.indexOf(";"));
        int rightSide = computeBytes(str);
        registers.put(leftSide, rightSide);
        System.out.println(leftSide + " " + rightSide + " registers");
    }

    private int getAddress(String str) {
        if (isNumber(str)) {
            return Integer.parseInt(str) / 4; //zustad ar vici es
        } else if (containsOperator(str)) {
            //an sp + a an a + sp an a + r an r + 1 an r1 + r2
            if (str.contains("SP")) {
                int res = computeResult(str);
                return res;
            }
            return computeResult(str) / 4;
        } else {
            if (str.startsWith("R") && !str.startsWith("RET") && !str.startsWith("RV")) {
                return registers.get(str) / 4;
            } else {
                return memory.length - 1;
            }
        }
    }

    private int getValueOfTheRightSide(String str) {
        if (isNumber(str)) { //loads constants.
            return Integer.parseInt(str);
        } else if (str.charAt(0) == '-' && isNumber(str.substring(1))) { //is negative number.
            return -1 * Integer.parseInt(str.substring(1));
        } else if (containsOperator(str) && !str.startsWith("M")) {
            //an sp + a an a + sp an a + r an r + 1 an r1 + r2
            return computeResult(str);
        } else if (str.startsWith("M")) {
            int res = getAddress(str.substring(str.indexOf('[') + 1, str.indexOf(']')));
            return memory[res];
        } else if(str.startsWith("RV")) {
            return rv;
        } else {
            if (str.startsWith("R") && !str.startsWith("RET")) {
                return registers.get(str);
            } else {
                return memory.length - 1;
            }
        }
    }

    private int computeResult(String str) {
        char operator = getOperator(str);
        int index = str.indexOf(operator);
        String first = str.substring(0, index);
        String second = str.substring(index + 1);
        if(first.startsWith("RV")){
            first = Integer.toString(rv);
        }
        if(second.startsWith("RV")){
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
            return findMemoryArrayIndex(operator, Integer.parseInt(first));
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

    private void deleteSavedPC(){
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
            return memory.length + Integer.parseInt(str.substring(index + 1)) / 4;
        } else {
            int index = str.indexOf("+");
            return memory.length - Integer.parseInt(str.substring(index + 1)) / 4;
        }
    }

    private String getLeftSide(String str) {
        if(str.startsWith("RET")){
            return "RET";
        }else if (str.startsWith("R")) {
            return str.substring(0, str.indexOf("="));
        } else if (str.startsWith("M")) {
            String address = str.substring(str.indexOf("[") + 1, str.indexOf("]"));
            int res = getAddress(address);
            return Integer.toString(res);
        } else if (str.startsWith("S")) {
            return str.substring(0, str.indexOf("="));
        }
        return "";
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

    public static void main(String[] args) throws IOException {
        AssemblyEmulator emulator = new AssemblyEmulator(new FileReader("\\Users\\mdzam\\Desktop\\assembly\\Assembly-Debugger\\Emulator\\src\\assembly.txt"));
    }
}
