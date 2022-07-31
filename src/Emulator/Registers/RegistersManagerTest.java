package src.Emulator.Registers;

import org.junit.jupiter.api.Assertions;

import java.util.Map;

class RegistersManagerTest {

    @org.junit.jupiter.api.Test
    void testAddAndGet() {
        RegistersManager registersManager = new RegistersManager();
        for (int i = 0; i < 100; i++) {
            registersManager.addRegister("R" + i, i);
        }
        for (int i = 0; i < 100; i++) {
            Assertions.assertTrue(registersManager.containsRegister("R" + i));
            Assertions.assertEquals(i, registersManager.getRegister("R" + i));
        }
    }

    @org.junit.jupiter.api.Test
    void testSetAndGetRv() {
        RegistersManager registersManager = new RegistersManager();
        for (int i = 0; i < 100; i++) {
            registersManager.setRv(i);
            Assertions.assertEquals(i, registersManager.getRv());
        }
    }

    @org.junit.jupiter.api.Test
    void getRegisters() {
        RegistersManager registersManager = new RegistersManager();
        for (int i = 0; i < 100; i++) {
            registersManager.addRegister("R" + i, i);
        }
        Map<String, Integer> mp = registersManager.getRegisters();
        for (int i = 0; i < 100; i++) {
            Assertions.assertTrue(mp.containsKey("R" + i));
            Assertions.assertEquals(i, (int) mp.get("R" + i));
        }
    }
}