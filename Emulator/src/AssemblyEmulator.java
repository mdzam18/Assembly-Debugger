import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AssemblyEmulator {

    private Map<String, Integer> registers;
    private ArrayList<Integer> memory;

    public AssemblyEmulator(FileReader file) throws IOException {
        ArrayList<String> list = readFile(file);
        registers = new HashMap<>();
        memory = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            processLine(list.get(i));
        }
        printMap();
    }

    private void processLine(String line){
        line = line.replaceAll(" ", ""); //Delete white spaces.
        if(!line.equals("")) {
            line = line.toUpperCase(Locale.ROOT);
            fillRegistersMaps(line);
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

    private String getInstructionType(String line) {
        return "";
    }

    private void fillRegistersMaps(String line) {
        int index = line.indexOf("=");
        String leftSide = getLeftSide(line.substring(0, index));
        String rightSide = getRightSide(line.substring(index + 1, line.indexOf(";")));
        registers.put(leftSide, Integer.parseInt(rightSide));
    }

    private String getLeftSide(String str) {
        if (str.startsWith("R")) {
            return str;
        } else if (str.startsWith("M")) {
            String address = str.substring(str.indexOf("[") + 1, str.indexOf("]"));
            int res = getAddress(address);
        }
        return "";
    }

    private int getAddress(String address) {
        if (isNumber(address)) {
            //amoige konkretuli misamartidan.
        } else if (Character.isDigit(address.charAt(0))) {
            //anu ragac aseti weria 2 + R1
            char operator = getOperator(address);
            int index = address.indexOf(operator);
            int a = Integer.parseInt(address.substring(0, index));
            int b = 0;
            if (address.charAt(index + 1) == 'R') {
                b = registers.get(address.substring(index + 1));
            } else {
                //anu sp-a.
            }
            return compute(a, b, operator);
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
            //sp-a.
        }
        return -1;
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
        } else if (str.charAt(0) == '-' && Character.isDigit(str.charAt(1))) { //is negative number.
            return str;
        } else if (containsOperator(str)) { //anu unda daviangarisho
            char operator = getOperator(str);
            int index = str.indexOf(operator);
            int a = registers.get(str.substring(0, index));
            int b = Integer.parseInt(str.substring(index + 1), str.length() - 1);
            return Integer.toString(compute(a, b, operator));
        } else if (str.startsWith("R")) {
            //anu marto registria
            return Integer.toString(registers.get(str));
        } else {
            //anu M-it iwyeba.
            int res = getAddress(str.substring(str.indexOf('[') + 1, str.indexOf(']')));
            //daabrune mag misamartze ra mnishvnelobaa
            return "";
        }
    }

    public static void main(String[] args) throws IOException {
        AssemblyEmulator emulator = new AssemblyEmulator(new FileReader("\\Users\\mdzam\\Desktop\\assembly\\Assembly-Debugger\\Emulator\\src\\assembly.txt"));
    }
}
