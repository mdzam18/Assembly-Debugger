package src.Emulator.Registers;

import java.util.HashMap;
import java.util.Map;

public class RegistersManager {

    private Map<String, Integer> registers;
    private int rv;
    private Map<String, Integer> registersWithSP; //remembers all registers witch contained sp during computation.

    public RegistersManager() {
        registers = new HashMap<>();
        registersWithSP = new HashMap<>();
    }

    public void addRegister(String leftSide, int rightSide) {
        registers.put(leftSide, rightSide);
    }

    public void removeRegister(String key){
        registersWithSP.remove(key);
    }

    //add registers value witch contained sp in computation.
    public void addRegisterInRegistersWithSP(String name, int value){
        registersWithSP.put(name, value);
    }

    public Map<String, Integer> getRegisters() {
        Map<String, Integer> mp = new HashMap<>();
        for(String key: registers.keySet()){
            mp.put(key, registers.get(key));
        }
        return mp;
    }

    public Map<String, Integer> getRegistersWithSP() {
        Map<String, Integer> mp = new HashMap<>();
        for(String key: registersWithSP.keySet()){
            mp.put(key, registersWithSP.get(key));
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
