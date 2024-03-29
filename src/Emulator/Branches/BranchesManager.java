package src.Emulator.Branches;

import src.Emulator.Numbers.NumbersManager;
import src.Emulator.Registers.RegistersManager;

public class BranchesManager {

    private RegistersManager registersManager;

    public BranchesManager(RegistersManager registersManager) {
        this.registersManager = registersManager;
    }

    public int branches(String str, int numberOfLine) throws Exception {
        String type = str.substring(1, 3);
        int index = str.indexOf(',');
        if (index == -1) {
            throw new Exception("missing values to compare");
        }
        int a = getValue(str.substring(3, index), numberOfLine);
        if (str.indexOf(',', index + 1) == -1) {
            if (str.contains("PC")) {
                throw new Exception("missing second value");
            } else {
                throw new Exception("missing pc");
            }
        }
        int b = getValue(str.substring(index + 1, str.indexOf(',', index + 1)), numberOfLine);
        int pc = getValue(str.substring(str.indexOf(',', index + 1) + 1, str.indexOf(";")), numberOfLine);
        return compareValues(type, a, b, pc);
    }

    //counts where to jump
    public int jump(String str, int numberOfLine) throws Exception {
        String s = str.substring(3, str.length() - 1);
        int pc = getValue(s, numberOfLine);
        return pc + 1;
    }

    public String asserts(String str, String currentFunction) throws Exception {
        String type = str.substring(1, 3);
        int index = str.indexOf(',');
        int a = getValue(str.substring(3, index), 0);
        int b = getValue(str.substring(index + 1, str.length() - 1), 0);
        int result = compareValues(type, a, b, 0);
        if (result == -1) {
            //assert
            int expected = b;
            int got = a;
            //System.out.println(expected);
            System.out.println(currentFunction.substring(8) + " " + "FAILED");
            String message = printExpectedMessage(expected, got, str.substring(1, 3));
            System.out.println(message);
            String res = currentFunction.substring(8) + " " + "FAILED" + "\n" + message;
            return res;
        } else {
            System.out.println(currentFunction.substring(8) + " " + "PASSED");
            return currentFunction.substring(8) + " " + "PASSED";
        }
    }

    private String printExpectedMessage(int expected, int got, String str) {
        if (str.startsWith("LT"))
            return "Expected less than: " + expected + " Got: " + got;
        if (str.startsWith("LE"))
            return "Expected less or equal than: " + expected + " Got: " + got;
        if (str.startsWith("EQ"))
            return "Expected: " + expected + " Got: " + got;
        if (str.startsWith("NE"))
            return "Expected not equal: " + expected + " Got: " + got;
        if (str.startsWith("GT"))
            return "Expected greater than: " + expected + " Got: " + got;
        if (str.startsWith("GE"))
            return "Expected greater or equal than: " + expected + " Got: " + got;
        return "";
    }

    private int getValue(String str, int numberOfLine) throws Exception {
        NumbersManager numbers = new NumbersManager();
        if (str.startsWith("M"))
            throw new Exception("invalid operation");
        if (str.startsWith("RV"))
            return registersManager.getRv();
        if (str.startsWith("R") && !str.startsWith("RET") && !str.startsWith("RV")) {
            if (registersManager.containsRegister(str))
                return registersManager.getRegister(str);
            throw new Exception("does not contain register: " + str);
        } else if (numbers.isNumber(str))
            return Integer.parseInt(str);
        if (numbers.containsOperator(str)) {
            char operator = numbers.getOperator(str);
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
}
