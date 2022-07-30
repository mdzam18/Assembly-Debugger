package src.Emulator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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