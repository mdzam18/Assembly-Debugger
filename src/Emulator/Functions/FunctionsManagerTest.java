package src.Emulator.Functions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import src.Emulator.Memory.MemoryManager;
import src.Emulator.Stack.StackManager;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;

//import static org.junit.Assert.assertThrows;

class FunctionsManagerTest {

    @Test
    public void processCall() throws Exception {
        ArrayList<String> list = new ArrayList<>();
        list.add("FUNCTIONMAIN;");
        list.add("SP=SP-4;");
        list.add("M[SP]=4;");
        list.add("CALL<A>;");
        list.add("SP=SP+4;");
        list.add("RET;");
        list.add("FUNCTIONA;");
        list.add("R1=2;");
        list.add("RET;");
        MemoryManager memoryManager = new MemoryManager();
        StackManager stackManager = new StackManager(memoryManager);
        FunctionsManager functionsManager = new FunctionsManager(list, memoryManager, stackManager);
        functionsManager.processCall("CALL<A>;", 3);
        Assertions.assertEquals("MAIN", stackManager.getCallStack().get(0));
        Assertions.assertEquals("A", stackManager.getCallStack().get(1));
        Assertions.assertEquals(2, stackManager.getCallStack().size());
    }

    @Test
    public void processReturns() throws Exception {
        ArrayList<String> list = new ArrayList<>();
        list.add("FUNCTIONMAIN;");
        list.add("SP=SP-4;");
        list.add("M[SP]=4;");
        list.add("CALL<A>;");
        list.add("SP=SP+4;");
        list.add("RET;");
        list.add("FUNCTIONA;");
        list.add("R1=2;");

        MemoryManager memoryManager = new MemoryManager();
        StackManager stackManager = new StackManager(memoryManager);
        Throwable exception = assertThrows(Exception.class, () -> new FunctionsManager(list, memoryManager, stackManager));
        Assertions.assertEquals("missing RET of: A", exception.getMessage());

        list.add("RET");

        FunctionsManager functionsManager = new FunctionsManager(list, memoryManager, stackManager);
        functionsManager.processCall("CALL<A>;", 3);
        Assertions.assertEquals(4, functionsManager.processReturns(8));
        functionsManager.processFunction("FUNCTIONMAIN;", 0);
        functionsManager.processCall("CALL<MAIN>;", 3);
        Assertions.assertEquals(-1, functionsManager.processReturns(5));
    }

    @Test
    public void getCurrentFunction() throws Exception {
        ArrayList<String> list = new ArrayList<>();
        list.add("FUNCTIONMAIN;");
        list.add("SP=SP-4;");
        list.add("M[SP]=4;");
        list.add("SP=SP+4;");
        list.add("RET;");
        MemoryManager memoryManager = new MemoryManager();
        StackManager stackManager = new StackManager(memoryManager);
        FunctionsManager functionsManager = new FunctionsManager(list, memoryManager, stackManager);
        Assertions.assertEquals("FUNCTIONMAIN", functionsManager.getCurrentFunction());
    }

    @Test
    public void getCurrentLine() throws Exception {
        ArrayList<String> list = new ArrayList<>();
        list.add("FUNCTIONMAIN;");
        list.add("SP=SP-4;");
        list.add("M[SP]=4;");
        list.add("SP=SP+4;");
        list.add("RET;");
        MemoryManager memoryManager = new MemoryManager();
        StackManager stackManager = new StackManager(memoryManager);
        FunctionsManager functionsManager = new FunctionsManager(list, memoryManager, stackManager);
        Assertions.assertEquals(0, functionsManager.getCurrentLine());
    }

    @Test
    public void processFunction() throws Exception {
        ArrayList<String> list = new ArrayList<>();
        list.add("FUNCTIONMAIN;");
        list.add("SP=SP-4;");
        list.add("M[SP]=4;");
        list.add("CALL<A>;");
        list.add("SP=SP+4;");
        list.add("RET;");
        list.add("FUNCTIONA;");
        list.add("R1=2;");
        list.add("RET;");

        MemoryManager memoryManager = new MemoryManager();
        StackManager stackManager = new StackManager(memoryManager);
        FunctionsManager functionsManager = new FunctionsManager(list, memoryManager, stackManager);
        Assertions.assertEquals(2, functionsManager.processFunction("FUNCTIONMAIN;", 0).size());
    }
}