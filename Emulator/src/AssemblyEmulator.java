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

    private void printMemory(){
        for (int i = 0 ; i < memory.length; i++){
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

    private void fillMemoryArray(int location, String line){
        if(location / 4 >= memory.length){
            System.out.println(location / 4 +  " " + memory.length);
            System.out.println("error");
        } else {
            String res = getRightSide(line.substring(line.indexOf("=") + 1, line.indexOf(";")));
            memory[location / 4] = Integer.parseInt(res);
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
        String leftSide = getLeftSide(line.substring(0, index));
        String rightSide = getRightSide(line.substring(index + 1, line.indexOf(";")));
        registers.put(leftSide, Integer.parseInt(rightSide));
    }

    private void allocateMemory(String line) {
        int index = line.indexOf("=");
        int size = computeMemorySize(line.substring(index + 1, line.indexOf(";")));
        resizeMemory(size);
    }

    private void resizeMemory(int size) {
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
            return str;
        } else if (str.startsWith("M")) {
            String address = str.substring(str.indexOf("[") + 1, str.indexOf("]"));
            int res = getAddress(address);
            return Integer.toString(res);
        } else if (str.startsWith("S")) {
            return str;
        }
        return "";
    }

    private int getAddress(String address) {
        if (isNumber(address)) {
            return Integer.parseInt(address);
        } else if (Character.isDigit(address.charAt(0))) {
            //anu ragac aseti weria 2 + R1
            char operator = getOperator(address);
            int index = address.indexOf(operator);
            int a = Integer.parseInt(address.substring(0, index));
            int b = 0;
            if (address.charAt(index + 1) == 'R') {
                b = registers.get(address.substring(index + 1));
                return compute(a, b, operator);
            } else {
                //anu a + sp.
                return a + 4 * memory.length;
            }
        } else if (address.startsWith("R")) {
            //sheidzleba marto registri iyos an r + 4
            if (containsOperator(address)) {
                char operator = getOperator(address);
                int index = address.indexOf(operator);
                int a = registers.get(address.substring(0, index));
                int b = Integer.parseInt(address.substring(index + 1));
                return compute(a, b, operator);
            } else {
                //anu marto registria
                return registers.get(address);
            }
        } else {
            //sp+a.
            if (containsOperator(address)) {
                char operator = getOperator(address);
                int index = address.indexOf(operator);
                int a = Integer.parseInt(address.substring(index + 1));
                if(operator == '+'){
                    return 4 * memory.length - a;
                }
                return a + 4 * memory.length;
            } else {
                return memory.length - 1;
            }
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
            if (!Character.isDigit(address.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private String getRightSide(String str) {
        if (isNumber(str)) { //loads constants.
            return str;
        } else if (str.charAt(0) == '-' && isNumber(str.substring(1))) { //is negative number.
            return str;
        } else if (containsOperator(str) && !str.startsWith("M")) { //anu unda daviangarisho
            char operator = getOperator(str);
            int index = str.indexOf(operator);
            int a = 0;
            int b = 0;
            if(registers.containsKey(str.substring(0, index))){
                a = registers.get(str.substring(0, index));
            } else if(str.startsWith("S")){
                a = memory.length - 1;
            } else {
                a = Integer.parseInt(str.substring(0, index));
            }
            if(registers.containsKey(str.substring(index + 1))){
                b = registers.get(str.substring(index + 1));
            } else if(str.charAt(index + 1) == 'S'){
                b = memory.length - 1;
            } else {
                b = Integer.parseInt(str.substring(index + 1));
            }
            return Integer.toString(compute(a, b, operator));
        } else if (str.startsWith("R")) {
            //anu marto registria
            return Integer.toString(registers.get(str));
        } else if(str.startsWith("S")) {
            return Integer.toString(memory.length - 1);
        } else {
            //anu M-it iwyeba.
            int res = getAddress(str.substring(str.indexOf('[') + 1, str.indexOf(']')));
            //daabrune mag misamartze ra mnishvnelobaa
            return Integer.toString(memory[res / 4]);
        }
    }

    public static void main(String[] args) throws IOException {
        AssemblyEmulator emulator = new AssemblyEmulator(new FileReader("\\Users\\mdzam\\Desktop\\assembly\\Assembly-Debugger\\Emulator\\src\\assembly.txt"));
    }
}
