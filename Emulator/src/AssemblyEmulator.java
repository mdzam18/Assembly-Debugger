import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AssemblyEmulator {

    private Map<String, Double> registers;
    private double[] memory;

    public AssemblyEmulator(FileReader file) throws IOException {
        ArrayList<String> list = readFile(file);
        registers = new HashMap<>();
        memory = new double[0];
        for (int i = 0; i < list.size(); i++) {
            int line = processLine(list.get(i), i);
            if(line != -1){
                i = line - 1;
                System.out.println("sasuke");
            }
            System.out.println("i " + i);
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

    private int processLine(String line, int numberOfLine) {
        line = line.replaceAll(" ", ""); //Delete white spaces.
        if (!line.equals("")) {
            line = line.toUpperCase(Locale.ROOT);
            String left = getLeftSide(line);
            if (left.startsWith("R")) {
                fillRegistersMaps(line);
            } else if (left.startsWith("S")) {
                allocateMemory(line);
            } else if (line.startsWith("M")) {
                fillMemoryArray(Double.parseDouble(left), line);
            } else if (line.startsWith("B")) {
                return branches(line, numberOfLine);
            }
        }
        return -1;
    }

    private int branches(String str, int numberOfLine) {
        String type = str.substring(0, 3);
        int index = str.indexOf(',');
        double a = getValue(str.substring(3, index), numberOfLine);
        double b = getValue(str.substring(index + 1, str.indexOf(',', index + 1)), numberOfLine);
        int pc = (int) getValue(str.substring(str.indexOf(',', index + 1) + 1, str.indexOf(";")), numberOfLine);
        System.out.println("a " + a + " b= " + b + " pc= " + pc );
        return compareValues(type, a, b, pc);
    }

    private int compareValues(String type, double n, double m, int pc) {
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

    private double getValue(String str, int numberOfLine) {
        if (str.startsWith("R")) {
            return registers.get(str);
        } else if (isNumber(str)) {
            return Double.parseDouble(str);
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

    private void fillMemoryArray(double location, String line) {
        if (location >= memory.length) {
            System.out.println(location + " " + memory.length);
            System.out.println("error");
        } else {
            String str = line.substring(line.indexOf("=") + 1, line.indexOf(";"));
            double res = computeBytes(str);
            memory[(int) location] = res;
        }
    }

    private double computeBytes(String str) {
        char c = '0';
        boolean toInt = false;
        if (str.startsWith(".")) {
            c = str.charAt(1);
            str = str.substring(2);
        } else if (str.startsWith("FTOI")) {
            toInt = true;
            str = str.substring(4);
        }
        double res = getValueOfTheRightSide(str);
        if (toInt) {
            System.out.println("naruto");
            int number = (int) res;
            res = number;
        }
        if (c != '0') {
            int n = c - '0';
            n = 32 - n * 8;
            System.out.println(res); //double-is saxit cota sxvanairi ricxvi iwereba da iyos ase?
            res = (int) res << n; //es vikitxo romel mxares aris sachiro gaweva.
        }
        return res;
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
        String str = line.substring(index + 1, line.indexOf(";"));
        double rightSide = computeBytes(str);
        registers.put(leftSide, rightSide);
        System.out.println(leftSide + "       " + rightSide);
    }

    private double getAddress(String str) {
        if (isNumber(str)) {
            return Integer.parseInt(str) / 4; //zustad ar vici es
        } else if (containsOperator(str)) {
            //an sp + a an a + sp an a + r an r + 1 an r1 + r2
            if (str.contains("SP")) {
                double res = computeResult(str);
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

    private double getValueOfTheRightSide(String str) {
        if (isNumber(str)) { //loads constants.
            return Double.parseDouble(str);
        } else if (str.charAt(0) == '-' && isNumber(str.substring(1))) { //is negative number.
            return -1 * Double.parseDouble(str.substring(1));
        } else if (containsOperator(str) && !str.startsWith("M")) {
            //an sp + a an a + sp an a + r an r + 1 an r1 + r2
            return computeResult(str);
        } else if (str.startsWith("M")) {
            double res = getAddress(str.substring(str.indexOf('[') + 1, str.indexOf(']')));
            return memory[(int) res];
        } else {
            if (str.startsWith("R")) {
                return registers.get(str);
            } else {
                return memory.length - 1;
            }
        }
    }

    private double computeResult(String str) {
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
            double value1 = registers.get(first);
            double value2 = registers.get(second);
            return compute(value1, value2, operator);
        }
    }

    private double computeValue(String first, String second, char operator, boolean isOrdered) {
        if (second.startsWith("S")) {
            return findMemoryArrayIndex(operator, Integer.parseInt(first));
        } else {
            //starts with R.
            double val = registers.get(second);
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
        double[] curr = new double[size];
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
            double res = getAddress(address);
            return Double.toString(res);
        } else if (str.startsWith("S")) {
            return str.substring(0, str.indexOf("="));
        }
        return "";
    }

    private boolean containsOperator(String address) {
        return address.indexOf('+') != -1 || address.indexOf('-') != -1 || address.indexOf('*') != -1 || address.indexOf('/') != -1;
    }

    private double compute(double a, double b, char operator) {
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
