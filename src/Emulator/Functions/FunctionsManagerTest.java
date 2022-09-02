package src.Emulator.Functions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import src.Emulator.Files.Lines;
import src.Emulator.Memory.MemoryManager;
import src.Emulator.Stack.StackManager;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;

//import static org.junit.Assert.assertThrows;

class FunctionsManagerTest {

    private void fillTheList(ArrayList<Lines> list) {
        Lines line = new Lines("FUNCTIONMAIN;", 0, "");
        Lines line2 = new Lines("SP=SP-4;", 0, "");
        Lines line3 = new Lines("M[SP]=4;", 0, "");
        Lines line4 = new Lines("CALL<A>;", 0, "");
        Lines line5 = new Lines("SP=SP+4;", 0, "");
        Lines line6 = new Lines("RET;", 0, "");
        Lines line7 = new Lines("FUNCTIONA;", 0, "");
        Lines line8 = new Lines("R1=2;", 0, "");
        Lines line9 = new Lines("RET;", 0, "");
        list.add(line);
        list.add(line2);
        list.add(line3);
        list.add(line4);
        list.add(line5);
        list.add(line6);
        list.add(line7);
        list.add(line8);
        list.add(line9);
    }

    @Test
    public void processCall() throws Exception {
        ArrayList<Lines> list = new ArrayList<>();
        fillTheList(list);
        MemoryManager memoryManager = new MemoryManager();
        StackManager stackManager = new StackManager(memoryManager);
        FunctionsManager functionsManager = new FunctionsManager(list, memoryManager, stackManager);
        functionsManager.processCall("CALL<A>;", 3);
        Assertions.assertEquals("MAIN", stackManager.getCallStack().get(0));
        Assertions.assertEquals("A", stackManager.getCallStack().get(1));
        Assertions.assertEquals(2, stackManager.getCallStack().size());
    }

    private void fillTheList2(ArrayList<Lines> list) {
        Lines line = new Lines("FUNCTIONMAIN;", 0, "");
        Lines line2 = new Lines("SP=SP-4;", 0, "");
        Lines line3 = new Lines("M[SP]=4;", 0, "");
        Lines line4 = new Lines("CALL<A>;", 0, "");
        Lines line5 = new Lines("SP=SP+4;", 0, "");
        Lines line6 = new Lines("RET;", 0, "");
        Lines line7 = new Lines("FUNCTIONA;", 0, "");
        Lines line8 = new Lines("R1=2;", 0, "");
        list.add(line);
        list.add(line2);
        list.add(line3);
        list.add(line4);
        list.add(line5);
        list.add(line6);
        list.add(line7);
        list.add(line8);
    }

    @Test
    public void processReturns() throws Exception {
        ArrayList<Lines> list = new ArrayList<>();
        fillTheList2(list);
        MemoryManager memoryManager = new MemoryManager();
        StackManager stackManager = new StackManager(memoryManager);
        Throwable exception = assertThrows(Exception.class, () -> new FunctionsManager(list, memoryManager, stackManager));
        Assertions.assertEquals("missing RET of: A", exception.getMessage());

        Lines line9 = new Lines("RET", 0, "");
        list.add(line9);

        FunctionsManager functionsManager = new FunctionsManager(list, memoryManager, stackManager);
        functionsManager.processCall("CALL<A>;", 3);
        Assertions.assertEquals(4, functionsManager.processReturns(8));
        functionsManager.processFunction("FUNCTIONMAIN;", 0);
        functionsManager.processCall("CALL<MAIN>;", 3);
        Assertions.assertEquals(-1, functionsManager.processReturns(5));
    }

    private void fillTheList3(ArrayList<Lines> list) {
        Lines line = new Lines("FUNCTIONMAIN;", 0, "");
        Lines line2 = new Lines("SP=SP-4;", 0, "");
        Lines line3 = new Lines("M[SP]=4;", 0, "");
        Lines line4 = new Lines("SP=SP+4;", 0, "");
        Lines line5 = new Lines("RET;", 0, "");

        list.add(line);
        list.add(line2);
        list.add(line3);
        list.add(line4);
        list.add(line5);
    }

    @Test
    public void getCurrentFunction() throws Exception {
        ArrayList<Lines> list = new ArrayList<>();
        fillTheList3(list);
        MemoryManager memoryManager = new MemoryManager();
        StackManager stackManager = new StackManager(memoryManager);
        FunctionsManager functionsManager = new FunctionsManager(list, memoryManager, stackManager);
        Assertions.assertEquals("FUNCTIONMAIN", functionsManager.getCurrentFunction());
    }

    private void fillTheList4(ArrayList<Lines> list) {
        Lines line = new Lines("FUNCTIONMAIN;", 0, "");
        Lines line2 = new Lines("SP=SP-4;", 0, "");
        Lines line3 = new Lines("M[SP]=4;", 0, "");
        Lines line4 = new Lines("SP=SP+4;", 0, "");
        Lines line5 = new Lines("RET;", 0, "");
        list.add(line);
        list.add(line2);
        list.add(line3);
        list.add(line4);
        list.add(line5);
    }

    @Test
    public void getCurrentLine() throws Exception {
        ArrayList<Lines> list = new ArrayList<>();
        fillTheList4(list);
        MemoryManager memoryManager = new MemoryManager();
        StackManager stackManager = new StackManager(memoryManager);
        FunctionsManager functionsManager = new FunctionsManager(list, memoryManager, stackManager);
        Assertions.assertEquals(0, functionsManager.getCurrentLine());
    }

    private void fillTheList5(ArrayList<Lines> list){
        Lines line = new Lines("FUNCTIONMAIN;", 0, "");
        Lines line2 = new Lines("SP=SP-4;", 0, "");
        Lines line3 = new Lines("M[SP]=4;", 0, "");
        Lines line4 = new Lines("CALL<A>;", 0, "");
        Lines line5 = new Lines("SP=SP+4;", 0, "");
        Lines line6 = new Lines("RET;", 0, "");
        Lines line7 = new Lines("FUNCTIONA;", 0, "");
        Lines line8 = new Lines("R1=2;", 0, "");
        Lines line9 = new Lines("RET;", 0, "");
        list.add(line);
        list.add(line2);
        list.add(line3);
        list.add(line4);
        list.add(line5);
        list.add(line6);
        list.add(line7);
        list.add(line8);
        list.add(line9);
    }

    @Test
    public void processFunction() throws Exception {
        ArrayList<Lines> list = new ArrayList<>();
        fillTheList5(list);
        MemoryManager memoryManager = new MemoryManager();
        StackManager stackManager = new StackManager(memoryManager);
        FunctionsManager functionsManager = new FunctionsManager(list, memoryManager, stackManager);
        Assertions.assertEquals(2, functionsManager.processFunction("FUNCTIONMAIN;", 0).size());
    }
}