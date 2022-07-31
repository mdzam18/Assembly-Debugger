package src.Emulator.Numbers;

public class NumbersManager {

    //returns arithmetic operator char
    public char getOperator(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '+' || str.charAt(i) == '-' || str.charAt(i) == '*' || str.charAt(i) == '/') {
                return str.charAt(i);
            }
        }
        return 0;
    }

    //checks if there is arithmetic operator
    public boolean containsOperator(String address) {
        return address.indexOf('+') != -1 || address.indexOf('-') != -1 || address.indexOf('*') != -1 || address.indexOf('/') != -1;
    }

    public boolean isNumber(String address) {
        for (int i = 0; i < address.length(); i++) {
            if (!Character.isDigit(address.charAt(i)) && address.charAt(i) != '.') {
                return false;
            }
        }
        return true;
    }
}
