package src.Emulator.Memory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

//import static org.junit.Assert.*;


class MemoryManagerTest {

    @Test
    public void getMemory() {
        MemoryManager memoryManager = new MemoryManager();
        memoryManager.resizeMemory(10);
        for (int i = 0; i < 10; i++) {
            memoryManager.addValue(i, i);
        }
        int[] memory = memoryManager.getMemory();
        for (int i = 0; i < 10; i++) {
            Assertions.assertEquals(i, memory[i]);
        }
    }

    @Test
    public void addAndGetValue() {
        MemoryManager memoryManager = new MemoryManager();
        memoryManager.resizeMemory(10);
        for (int i = 0; i < 10; i++) {
            memoryManager.addValue(i, i);
        }
        Assertions.assertEquals(10, memoryManager.getMemoryArraySize());
        for (int i = 0; i < 10; i++) {
            Assertions.assertEquals(i, memoryManager.getValue(i));
        }
    }

    @Test
    public void allocateMemory() throws Exception {
        MemoryManager memoryManager = new MemoryManager();
        memoryManager.allocateMemory("SP=SP-8;");
        Assertions.assertEquals(3, memoryManager.getMemoryArraySize());
        memoryManager.allocateMemory("SP=SP+4;");
        Assertions.assertEquals(2, memoryManager.getMemoryArraySize());
        memoryManager.allocateMemory("SP=SP+4;");
        Assertions.assertEquals(1, memoryManager.getMemoryArraySize());
        Throwable exception = assertThrows(Exception.class, () -> memoryManager.allocateMemory("SP=SP+16;"));
        Assertions.assertEquals("invalid size: -3", exception.getMessage());
    }

    @Test
    public void resizeMemory() {
        MemoryManager memoryManager = new MemoryManager();
        Assertions.assertEquals(1, memoryManager.getMemoryArraySize());
        for (int i = 1; i < 10; i++) {
            memoryManager.resizeMemory(i);
            Assertions.assertEquals(i, memoryManager.getMemoryArraySize());
        }
    }

    @Test
    public void deleteSavedPC() {
        MemoryManager memoryManager = new MemoryManager();
        memoryManager.deleteSavedPC();
        Assertions.assertEquals(0, memoryManager.getMemoryArraySize());
    }
}