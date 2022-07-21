package src.Emulator;

public class BranchesManager {

    private RegistersManager registersManager;
    private MemoryManager memoryManager;

    public BranchesManager(RegistersManager registersManager, MemoryManager memoryManager) {
        this.registersManager = registersManager;
        this.memoryManager = memoryManager;
    }

    public int branches(String str, int numberOfLine) throws Exception {
        String type = str.substring(1, 3);
        int index = str.indexOf(',');
        int a = getValue(str.substring(3, index), numberOfLine);
        int b = getValue(str.substring(index + 1, str.indexOf(',', index + 1)), numberOfLine);
        int pc = getValue(str.substring(str.indexOf(',', index + 1) + 1, str.indexOf(";")), numberOfLine);
        return compareValues(type, a, b, pc);
    }

    public int jump(String str, int numberOfLine) throws Exception {
        String s = str.substring(3, str.length() - 1);
        int pc = getValue(s, numberOfLine);
        return pc + 1;
    }

    public void asserts(String str, String currentFunction) throws Exception {
        String type = str.substring(1, 3);
        int index = str.indexOf(',');
        int a = getValue(str.substring(3, index), 0);
        int b = getValue(str.substring(index + 1, str.length() - 1), 0);
        int result = compareValues(type, a, b, 0);
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

    private int getValue(String str, int numberOfLine) throws Exception {
        NumbersManager numbers = new NumbersManager();
        if (str.startsWith("M")) {
            throw new Exception("invalid operation");
        }
        if (str.startsWith("RV")) {
            return registersManager.getRv();
        }
        if (str.startsWith("R") && !str.startsWith("RET") && !str.startsWith("RV")) {
            if (registersManager.containsRegister(str)) {
                return registersManager.getRegister(str);
            } else {
                throw new Exception("invalid register");
            }
        } else if (numbers.isNumber(str)) {
            return Integer.parseInt(str);
        } else {
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