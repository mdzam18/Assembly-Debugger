import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AssemblyEmulator {

    private Map<String, Integer> registers;
    private int[] memory;

    public AssemblyEmulator(FileReader file) throws IOException {
        ArrayList<String> list = readFile(file);
        registers = new HashMap<>();
        memory = new int[0];
        for (int i = 0; i < list.size(); i++) {
            processLine(list.get(i));
        }
        printMap();
        printMemory();
    }

    private void printMemory() {
        for (int i = 0; i < memory.length; i++) {
            System.out.println(i);
            System.out.println(memory[i]);
        }
    }

    private void processLine(String line) {
        line = line.replaceAll(" ", ""); //Delete white spaces.
        if (!line.equals("")) {
            line = line.toUpperCase(Locale.ROOT);
            String left = getLeftSide(line);
            if (left.startsWith("R")) {
                fillRegistersMaps(line);
            } else if (left.startsWith("S")) {
                allocateMemory(line);
            } else if (line.startsWith("M")) {
                fillMemoryArray(Integer.parseInt(left), line);
            }
        }
    }

    private void fillMemoryArray(int location, String line) {
        if (location >= memory.length) {
            System.out.println(location + " " + memory.length);
            System.out.println("error");
        } else {
            int res = getValueOfTheRightSide(line.substring(line.indexOf("=") + 1, line.indexOf(";")));
            memory[location] = res;
        }
    }

    private ArrayList<String> readFile(FileReader file) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader rd = new BufferedReader(file);
        while (true) {
            String line = rd.readLine();
            if (line == null) {
                break;
            }
            list.add(line);
        }
        rd.close();
        return list;
    }

    private void printMap() {
        for (String key : registers.keySet()) {
            System.out.println(key + " " + registers.get(key));
        }
    }

    private void fillRegistersMaps(String line) {
        int index = line.indexOf("=");
        String leftSide = line.substring(0, index);
        int rightSide = getValueOfTheRightSide(line.substring(index + 1, line.indexOf(";")));
        registers.put(leftSide, rightSide);
        System.out.println(leftSide + "       " + rightSide);
    }

    private int getAddress(String str) {
        if (isNumber(str)) {
            return Integer.parseInt(str) / 4; //zustad ar vici es
        } else if (containsOperator(str)) {
            //an sp + a an a + sp an a + r an r + 1 an r1 + r2
            if(str.contains("SP")){
                int res = computeResult(str);
                return res;
            }
            return computeResult(str) / 4;
        } else {
            if (str.startsWith("R")) {
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
        } else {
            if (str.startsWith("R")) {
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

    private void resizeMemory(int size) {
        System.out.println("size " + size);
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
        if (str.startsWith("R")) {
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
            if (!Character.isDigit(address.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        AssemblyEmulator emulator = new AssemblyEmulator(new FileReader("\\Users\\mdzam\\Desktop\\assembly\\Assembly-Debugger\\Emulator\\src\\assembly.txt"));
    }
}
