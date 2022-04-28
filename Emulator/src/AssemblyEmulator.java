import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AssemblyEmulator {

    private Map<String, Integer> registers;

    public AssemblyEmulator(String text) {
        text = text.replaceAll(" ", ""); //Delete white spaces.
        text = text.toUpperCase(Locale.ROOT);
        registers = new HashMap<>();
        fillRegistersMaps(text);
        printMap();
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
        //loads constants.
        if (Character.isDigit(str.charAt(0))) {
            return str;
        } else if (str.charAt(0) == '-' && Character.isDigit(str.charAt(1))) { //is negative number.
            return str;
        }
        return "";
    }

    public static void main(String[] args) {
        AssemblyEmulator emulator = new AssemblyEmulator("R1 =- 5  ;");
    }
}
