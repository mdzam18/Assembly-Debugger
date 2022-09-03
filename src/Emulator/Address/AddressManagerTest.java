package src.Emulator.Address;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import src.Emulator.Memory.MemoryManager;
import src.Emulator.Registers.RegistersManager;

import static org.junit.jupiter.api.Assertions.assertThrows;

//import static org.junit.Assert.assertThrows;

class AddressManagerTest {

    @Test
    public void getAddress() throws Exception {
        RegistersManager registersManager = new RegistersManager();
        MemoryManager memoryManager = new MemoryManager();
        registersManager.addRegister("R1", 4);
        registersManager.addRegister("R2", 4);
        registersManager.setRv(4);
        memoryManager.resizeMemory(3);
        AddressManager addressManager = new AddressManager(registersManager, memoryManager);

        Assertions.assertEquals(3, addressManager.getAddress("SP-4"));
        Assertions.assertEquals(1, addressManager.getAddress("SP+4"));
        Assertions.assertEquals(1, addressManager.getAddress("R1"));
        Throwable exception = assertThrows(Exception.class, () -> addressManager.getAddress("R3"));
        Assertions.assertEquals("invalid register", exception.getMessage());
        Assertions.assertEquals(2, addressManager.getAddress("SP"));
        Assertions.assertEquals(1, addressManager.getAddress("4"));
        Assertions.assertEquals(0, addressManager.getAddress("R1-4"));
        Assertions.assertEquals(2, addressManager.getAddress("R1+4"));
        Assertions.assertEquals(2, addressManager.getAddress("4+R1"));
        Assertions.assertEquals(2, addressManager.getAddress("R1+R2"));
        Assertions.assertEquals(1, addressManager.getAddress("RV"));
        Assertions.assertEquals(2, addressManager.getAddress("RV+4"));
        Assertions.assertEquals(2, addressManager.getAddress("4+RV"));
        Assertions.assertEquals(0, addressManager.getAddress("4-RV"));

        exception = assertThrows(Exception.class, () -> addressManager.getAddress("R3+4"));
        Assertions.assertEquals("does not contain register: R3", exception.getMessage());

        exception = assertThrows(Exception.class, () -> addressManager.getAddress("4+R3"));
        Assertions.assertEquals("does not contain register: R3", exception.getMessage());

        exception = assertThrows(Exception.class, () -> addressManager.getAddress("R1+R3"));
        Assertions.assertEquals("does not contain register: R3", exception.getMessage());

        exception = assertThrows(Exception.class, () -> addressManager.getAddress("M[1] + 2"));
        Assertions.assertEquals("should not contain M in computation", exception.getMessage());
    }

    @Test
    public void computeBytes() throws Exception {
        RegistersManager registersManager = new RegistersManager();
        registersManager.addRegister("R1", 2);
        registersManager.addRegister("R2", 3);
        MemoryManager memoryManager = new MemoryManager();
        memoryManager.addValue(0, 5);
        AddressManager addressManager = new AddressManager(registersManager, memoryManager);
        Assertions.assertEquals(2, addressManager.computeBytes(".2R1"));
        Assertions.assertEquals(2, addressManager.computeBytes("FTOI2.3"));
    }

    @Test
    public void getValueOfTheRightSide() throws Exception {
        RegistersManager registersManager = new RegistersManager();
        registersManager.addRegister("R1", 2);
        registersManager.addRegister("R2", 3);
        registersManager.setRv(3);
        MemoryManager memoryManager = new MemoryManager();
        memoryManager.addValue(0, 5);
        AddressManager addressManager = new AddressManager(registersManager, memoryManager);

        Assertions.assertEquals(5, addressManager.getValueOfTheRightSide("5"));
        Assertions.assertEquals(-5, addressManager.getValueOfTheRightSide("-5"));
        Assertions.assertEquals(1, addressManager.getValueOfTheRightSide("R2-R1"));

        Throwable exception = assertThrows(Exception.class, () -> addressManager.getValueOfTheRightSide("M[5]"));
        Assertions.assertEquals("invalid address", exception.getMessage());

        Assertions.assertEquals(5, addressManager.getValueOfTheRightSide("M[SP]"));
        Assertions.assertEquals(3, addressManager.getValueOfTheRightSide("RV"));

        exception = assertThrows(Exception.class, () -> addressManager.getValueOfTheRightSide("R3"));
        Assertions.assertEquals("invalid register", exception.getMessage());
        Assertions.assertEquals(2, addressManager.getValueOfTheRightSide("R1"));
        Assertions.assertEquals(0, addressManager.getValueOfTheRightSide("SP"));

        Assertions.assertEquals(2, addressManager.getValueOfTheRightSide("SP+R1"));
        Assertions.assertEquals(2, addressManager.getValueOfTheRightSide("R1+SP"));
    }

    @Test
    public void getLeftSide() throws Exception {
        MemoryManager memoryManager = new MemoryManager();
        RegistersManager registersManager = new RegistersManager();
        AddressManager addressManager = new AddressManager(registersManager, memoryManager);
        Assertions.assertEquals("RET", addressManager.getLeftSide("RET"));

        Throwable exception = assertThrows(Exception.class, () -> addressManager.getLeftSide("Rk"));
        Assertions.assertEquals("invalid register, do you mean RV?", exception.getMessage());

        Assertions.assertEquals("R1", addressManager.getLeftSide("R1=2;"));

        exception = assertThrows(Exception.class, () -> addressManager.getLeftSide("R1"));
        Assertions.assertEquals("missing =", exception.getMessage());

        exception = assertThrows(Exception.class, () -> addressManager.getLeftSide("M2"));
        Assertions.assertEquals("missing [", exception.getMessage());

        exception = assertThrows(Exception.class, () -> addressManager.getLeftSide("M[2"));
        Assertions.assertEquals("missing ]", exception.getMessage());

        exception = assertThrows(Exception.class, () -> addressManager.getLeftSide("M[1]"));
        Assertions.assertEquals("missing =", exception.getMessage());

        Assertions.assertEquals("0", addressManager.getLeftSide("M[SP]=2;"));

        Assertions.assertEquals("SP", addressManager.getLeftSide("SP=sp-2;"));

        exception = assertThrows(Exception.class, () -> addressManager.getLeftSide("SP"));
        Assertions.assertEquals("missing =", exception.getMessage());
    }

    @Test
    public void fillMemoryArray() throws Exception {
        RegistersManager registersManager = new RegistersManager();
        MemoryManager memoryManager = new MemoryManager();
        AddressManager addressManager = new AddressManager(registersManager, memoryManager);
        addressManager.fillMemoryArray(0, "M[sp]=2;");
        Assertions.assertEquals(memoryManager.getValue(0), 2);

        Throwable exception = assertThrows(Exception.class, () -> addressManager.fillMemoryArray(-1, ""));
        Assertions.assertEquals("-1 out of bounds", exception.getMessage());

        exception = assertThrows(Exception.class, () -> addressManager.fillMemoryArray(3, ""));
        Assertions.assertEquals("3 out of bounds", exception.getMessage());
    }
}