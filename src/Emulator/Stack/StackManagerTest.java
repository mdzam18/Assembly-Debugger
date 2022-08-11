package src.Emulator.Stack;

import org.junit.jupiter.api.Test;
import src.Emulator.Memory.MemoryManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StackManagerTest {

    @Test
    void showStack() throws Exception {
        MemoryManager memoryManager = new MemoryManager();
        memoryManager.resizeMemory(4);
        memoryManager.addValue(0, 0);
        memoryManager.addValue(1, 1);
        memoryManager.addValue(2, 2);
        memoryManager.addValue(3, 3);
        StackManager stackManager = new StackManager(memoryManager);
        stackManager.addSavedPc(2);
        stackManager.addFunctionName("a");
        List<String> list = stackManager.showStack("MAIN");
        assertEquals("SAVED PC", list.get(0));
        assertEquals("1", list.get(1));

        list = stackManager.showStack("a");
        assertEquals("SAVED PC", list.get(0));
        assertEquals("3", list.get(1));
        assertEquals(2, list.size());

        stackManager.removeSavedPc(1);
        stackManager.addSavedPc(1);
        stackManager.addSavedPc(3);
        list = stackManager.showStack("a");
        assertEquals("SAVED PC", list.get(0));
        assertEquals("2", list.get(1));
        assertEquals(2, list.size());
    }

    @Test
    void testCallStack() {
        MemoryManager memoryManager = new MemoryManager();
        StackManager stackManager = new StackManager(memoryManager);
        stackManager.addFunctionName("a");
        List<String> callStack = stackManager.getCallStack();
        assertEquals("MAIN", callStack.get(0));
        assertEquals("A", callStack.get(1));
        stackManager.removeFunctionName(1);
        callStack = stackManager.getCallStack();
        assertEquals(1, callStack.size());
        assertEquals("MAIN", callStack.get(0));
        stackManager.removeFunctionName(0);
        callStack = stackManager.getCallStack();
        assertEquals(0, callStack.size());
    }

}