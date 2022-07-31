package src.Emulator.Registers;

import java.util.HashMap;
import java.util.Map;

public class RegistersManager {

    private Map<String, Integer> registers;
    private int rv;

    public RegistersManager() {
        registers = new HashMap<>();
    }

    public void addRegister(String leftSide, int rightSide) {
        registers.put(leftSide, rightSide);
    }

    public Map<String, Integer> getRegisters() {
        Map<String, Integer> mp = new HashMap<>();
        for(String key: registers.keySet()){
            mp.put(key, registers.get(key));
        }
        return mp;
    }

    public int getRv() {
        return rv;
    }

    public void setRv(int value) {
        rv = value;
    }

    public boolean containsRegister(String r) {
        return registers.containsKey(r);
    }

    public int getRegister(String r) {
        return registers.get(r);
    }
}
