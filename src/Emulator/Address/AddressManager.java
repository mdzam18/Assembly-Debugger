package src.Emulator.Address;

import src.Emulator.Memory.MemoryManager;
import src.Emulator.Numbers.NumbersManager;
import src.Emulator.Registers.RegistersManager;

public class AddressManager {

    private RegistersManager registersManager;
    private MemoryManager memoryManager;

    public AddressManager(RegistersManager registersManager, MemoryManager memoryManager) {
        this.registersManager = registersManager;
        this.memoryManager = memoryManager;
    }

    //computes address where to write value
    public int getAddress(String str) throws Exception {
        NumbersManager numbers = new NumbersManager();
        if (numbers.isNumber(str)) {
            return Integer.parseInt(str) / 4; //zustad ar vici es
        } else if (numbers.containsOperator(str)) {
            //sp + const or const + sp or const + r or r + const or r1 + r2
            return computeResult(str) / 4;
        } else {
            if (str.startsWith("R") && !str.startsWith("RET") && !str.startsWith("RV")) {
                if (registersManager.containsRegister(str)) {
                    return registersManager.getRegister(str) / 4;
                } else {
                    throw new Exception("invalid register");
                }
            } else {
                return memoryManager.getMemoryArraySize() - 1;
            }
        }
    }

    public int computeBytes(String str) throws Exception {
        char c = '0';
        if (str.startsWith(".")) {
            c = str.charAt(1);
            str = str.substring(2);
        } else if (str.startsWith("FTOI")) {
            str = str.substring(4);
        }
        int res = getValueOfTheRightSide(str);
        //get certain amount of bytes
        if (c != '0') {
            int n = c - '0';
            n = 32 - n * 8;
            res = res << n;
            res = res >> n;
        }
        return res;
    }

    //computes value
    public int getValueOfTheRightSide(String str) throws Exception {
        NumbersManager numbers = new NumbersManager();
        if (numbers.isNumber(str)) { //loads constants.
            return Integer.parseInt(str);
        } else if (str.charAt(0) == '-' && numbers.isNumber(str.substring(1))) { //is negative number.
            return -1 * Integer.parseInt(str.substring(1));
        } else if (numbers.containsOperator(str) && !str.startsWith("M")) {
            //sp + const or const + sp or const + r or r + const or r1 + r2
            return computeResult(str);
        } else if (str.startsWith("M")) {
            int res = getAddress(str.substring(str.indexOf('[') + 1, str.indexOf(']')));
            if (res >= memoryManager.getMemoryArraySize()) {
                throw new Exception("invalid address");
            }
            return memoryManager.getValue(res);
        } else if (str.startsWith("RV")) {
            return registersManager.getRv();
        } else {
            if (str.startsWith("R") && !str.startsWith("RET")) {
                if (!registersManager.containsRegister(str)) {
                    throw new Exception("invalid register");
                }
                return registersManager.getRegister(str);
            } else {
                return (memoryManager.getMemoryArraySize() - 1) * 4;
            }
        }
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

    public String getLeftSide(String str) throws Exception {
        NumbersManager numbers = new NumbersManager();
        if (str.startsWith("RET")) {
            return "RET";
        } else if (str.startsWith("R")) {
            if(str.charAt(1) != 'V' && !numbers.isNumber("" + str.charAt(1))){
                throw new Exception("invalid register. do you mean RV?");
            } else {
                return str.substring(0, str.indexOf("="));
            }
        } else if (str.startsWith("M")) {
            if (!str.contains("[")){
                throw new Exception("should contain [");
            }
            if (!str.contains("]")){
                throw new Exception("should contain ]");
            }
            String address = str.substring(str.indexOf("[") + 1, str.indexOf("]"));
            int res = getAddress(address);
            return Integer.toString(res);
        } else if (str.startsWith("S")) {
            if(!str.contains("=")){
                throw new Exception("should contain =");
            }
            return str.substring(0, str.indexOf("="));
        } else {
            return "";
        }
    }

    //sp + const or const + sp or const + r or r + const or r1 + r2
    private int computeResult(String str) throws Exception {
        NumbersManager numbers = new NumbersManager();
        char operator = numbers.getOperator(str);
        if (str.contains("M")){
            throw new Exception("should not contain M in computation");
        }
        if(operator == 0){
            throw new Exception("should contain operator");
        }
        int index = str.indexOf(operator);
        String first = str.substring(0, index);
        String second = str.substring(index + 1);
        if (first.startsWith("RV")) {
            first = Integer.toString(registersManager.getRv());
        }
        if (second.startsWith("RV")) {
            second = Integer.toString(registersManager.getRv());
        }
        if (numbers.isNumber(first)) {
            return computeValue(first, second, operator, true);
        } else if (numbers.isNumber(second)) {
            return computeValue(second, first, operator, false);
        } else if (numbers.isNumber(first) && numbers.isNumber(second)) {
            return Integer.parseInt(first) + Integer.parseInt(second);
        } else {
            if(!registersManager.containsRegister(first)){
                throw new Exception("does not contain register: " + first);
            }
            if(!registersManager.containsRegister(second)){
                throw new Exception("does not contain register: " + second);
            }
            int value1 = registersManager.getRegister(first);
            int value2 = registersManager.getRegister(second);
            return compute(value1, value2, operator);
        }
    }

    private int findMemoryArrayIndex(char operator, int number) {
        if (operator == '+') {
            return memoryManager.getMemoryArraySize() - number / 4 - 1;
        } else {
            return memoryManager.getMemoryArraySize() + number / 4 - 1;
        }
    }

    private int computeValue(String first, String second, char operator, boolean isOrdered) throws Exception {
        if (second.startsWith("S")) {
            return findMemoryArrayIndex(operator, Integer.parseInt(first)) * 4;
        } else {
            //starts with R.
            if(!registersManager.containsRegister(second)){
                throw new Exception("does not contain register: " + second);
            }
            int val = registersManager.getRegister(second);
            if (isOrdered) {
                return compute(Integer.parseInt(first), val, operator);
            } else {
                return compute(val, Integer.parseInt(first), operator);
            }
        }
    }

    //adds value to the memory array
    public void fillMemoryArray(int location, String line) throws Exception {
        if (location >= memoryManager.getMemoryArraySize()) {
            System.out.println("location " + location + " " + "memory length " + memoryManager.getMemoryArraySize());
            throw new Exception("out of bounds.");
        } else {
            String str = line.substring(line.indexOf("=") + 1, line.indexOf(";"));
            int res = computeBytes(str);
            memoryManager.addValue(location, res);
        }
    }
}
