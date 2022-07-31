package src.Emulator.Address;

import org.junit.jupiter.api.Test;
import src.Emulator.Memory.MemoryManager;
import src.Emulator.Registers.RegistersManager;

class AddressManagerTest {

    @Test
    void getAddress() {
        RegistersManager registersManager = new RegistersManager();
        MemoryManager memoryManager = new MemoryManager();
        AddressManager addressManager = new AddressManager(registersManager, memoryManager);

    }

    @Test
    void computeBytes() {
    }

    @Test
    void getValueOfTheRightSide() {
    }

    @Test
    void getLeftSide() {
    }

    @Test
    void fillMemoryArray() {
    }
}