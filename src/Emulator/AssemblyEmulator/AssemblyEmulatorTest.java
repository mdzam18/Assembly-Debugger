package src.Emulator.AssemblyEmulator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;

//import static org.junit.Assert.assertThrows;

class AssemblyEmulatorTest {

    @Test
    public void getRegisters() throws Exception {
        testAddNumbers();
        testAdd2();
        testFactorial();
        testTestClasses();
        testForLoop();
        testCompare();
    }

    private void testForLoop() throws Exception {
        String[] args = new String[1];
        args[0] = "src/Tests/forLoop.asm";
        AssemblyEmulator assemblyEmulator = new AssemblyEmulator(args);
        assemblyEmulator.debug();
        Map<String, Integer> registers = assemblyEmulator.getRegisters();
        Assertions.assertEquals(0, assemblyEmulator.getRv());
        Assertions.assertEquals(4, registers.size());
        Assertions.assertEquals(3, registers.get("R1"));
        Assertions.assertEquals(3, registers.get("R2"));
        Assertions.assertEquals(3, registers.get("R3"));
        Assertions.assertEquals(4, registers.get("R9"));
    }

    //compares two registers
    private void testCompare() throws Exception {
        String[] args = new String[1];
        args[0] = "src/Tests/compare.asm";
        AssemblyEmulator assemblyEmulator = new AssemblyEmulator(args);
        assemblyEmulator.debug();
        Map<String, Integer> registers = assemblyEmulator.getRegisters();
        Assertions.assertEquals(0, assemblyEmulator.getRv());
        Assertions.assertEquals(3, registers.size());
        Assertions.assertEquals(3, registers.get("R1"));
        Assertions.assertEquals(2, registers.get("R2"));
        Assertions.assertEquals(3, registers.get("R3"));
    }


    //adds two registers
    private void testAddNumbers() throws Exception {
        String[] args = new String[1];
        args[0] = "src/Tests/addNumbers.asm";
        AssemblyEmulator assemblyEmulator = new AssemblyEmulator(args);
        assemblyEmulator.debug();
        Map<String, Integer> registers = assemblyEmulator.getRegisters();
        Assertions.assertEquals(5, assemblyEmulator.getRv());
        Assertions.assertEquals(4, registers.size());
        Assertions.assertEquals(2, registers.get("R1"));
        Assertions.assertEquals(3, registers.get("R2"));
        Assertions.assertEquals(3, registers.get("R3"));
    }

    private void testFactorial() throws Exception {
        String[] args = new String[1];
        args[0] = "src/Tests/factorial.asm";
        AssemblyEmulator assemblyEmulator = new AssemblyEmulator(args);
        assemblyEmulator.debug();
        Map<String, Integer> registers = assemblyEmulator.getRegisters();
        Assertions.assertEquals(120, assemblyEmulator.getRv());
        Assertions.assertEquals(4, registers.size());
        Assertions.assertEquals(5, registers.get("R1"));
        Assertions.assertEquals(0, registers.get("R2"));
        Assertions.assertEquals(120, registers.get("R3"));
    }

    //calls method in method and uses rv
    private void testAdd2() throws Exception {
        String[] args = new String[1];
        args[0] = "src/Tests/add2.asm";
        AssemblyEmulator assemblyEmulator = new AssemblyEmulator(args);
        assemblyEmulator.debug();
        Map<String, Integer> registers = assemblyEmulator.getRegisters();
        Assertions.assertEquals(8, assemblyEmulator.getRv());
        Assertions.assertEquals(4, registers.size());
        Assertions.assertEquals(2, registers.get("R1"));
        Assertions.assertEquals(3, registers.get("R2"));
        Assertions.assertEquals(5, registers.get("R3"));
    }

    private void testTestClasses() throws Exception {
        String[] args = new String[2];
        args[0] = "src/Tests/callTests.asm";
        args[1] = "src/Tests/sum.asm";
        AssemblyEmulator assemblyEmulator = new AssemblyEmulator(args);
        assemblyEmulator.debug();
        Map<String, Integer> registers = assemblyEmulator.getRegisters();
        Assertions.assertEquals(12, assemblyEmulator.getRv());
        Assertions.assertEquals(3, registers.size());
        Assertions.assertEquals(5, registers.get("R1"));
        Assertions.assertEquals(7, registers.get("R2"));
    }

    @Test
    public void showStack() throws Exception {
        String[] args = new String[1];
        args[0] = "src/Tests/addNumbers.asm";
        AssemblyEmulator assemblyEmulator = new AssemblyEmulator(args);
        testMain(assemblyEmulator);
        assemblyEmulator = new AssemblyEmulator(args);
        testAdd(assemblyEmulator);
    }

    //checks adds stack
    private void testAdd(AssemblyEmulator assemblyEmulator) throws Exception {
        for (int i = 0; i < 11; i++) {
            assemblyEmulator.next();
        }
        List<String> list = assemblyEmulator.showStack("ADD");
        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals("SAVED PC", list.get(0));

        assemblyEmulator.next();
        assemblyEmulator.next();
        assemblyEmulator.next();
        list = assemblyEmulator.showStack("ADD");
        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals("SAVED PC", list.get(0));

        assemblyEmulator.next();
        Throwable exception = assertThrows(Exception.class, () -> assemblyEmulator.showStack("ADD"));
        Assertions.assertEquals("can't show stack of this function", exception.getMessage());
    }

    //checks mains stack
    private void testMain(AssemblyEmulator assemblyEmulator) throws Exception {
        assemblyEmulator.next();
        List<String> list = assemblyEmulator.showStack("MAIN");
        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals("SAVED PC", list.get(0));
        for (int i = 0; i < 4; i++) {
            assemblyEmulator.next();
        }
        list = assemblyEmulator.showStack("MAIN");
        Assertions.assertEquals(3, list.size());
        Assertions.assertEquals("SAVED PC", list.get(0));
        Assertions.assertEquals("2", list.get(1));
        Assertions.assertEquals("3", list.get(2));
        for (int i = 0; i < 5; i++) {
            assemblyEmulator.next();
        }
        list = assemblyEmulator.showStack("MAIN");
        Assertions.assertEquals(5, list.size());
        Assertions.assertEquals("SAVED PC", list.get(0));
        Assertions.assertEquals("2", list.get(1));
        Assertions.assertEquals("3", list.get(2));
        Assertions.assertEquals("3", list.get(3));
        Assertions.assertEquals("2", list.get(4));
        for (int i = 0; i < 5; i++) {
            assemblyEmulator.next();
        }
        list = assemblyEmulator.showStack("MAIN");
        Assertions.assertEquals(5, list.size());
        Assertions.assertEquals("SAVED PC", list.get(0));
        Assertions.assertEquals("2", list.get(1));
        Assertions.assertEquals("3", list.get(2));
        Assertions.assertEquals("3", list.get(3));
        Assertions.assertEquals("2", list.get(4));

        assemblyEmulator.next();
        list = assemblyEmulator.showStack("MAIN");
        Assertions.assertEquals(3, list.size());
        Assertions.assertEquals("SAVED PC", list.get(0));
        Assertions.assertEquals("2", list.get(1));
        Assertions.assertEquals("3", list.get(2));

        assemblyEmulator.next();
        list = assemblyEmulator.showStack("MAIN");
        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals("SAVED PC", list.get(0));

        assemblyEmulator.next();
        assemblyEmulator.next();
        Throwable exception = assertThrows(Exception.class, () -> assemblyEmulator.showStack("ADD"));
        Assertions.assertEquals("can't show stack of this function", exception.getMessage());
    }

    @Test
    public void getCallStack() throws Exception {
        String[] args = new String[1];
        args[0] = "src/Tests/addNumbers.asm";
        AssemblyEmulator assemblyEmulator = new AssemblyEmulator(args);
        for(int i = 0; i < 10; i++){
            assemblyEmulator.next();
            List<String> callStack = assemblyEmulator.getCallStack();
            Assertions.assertEquals(1, callStack.size());
            Assertions.assertEquals("MAIN", callStack.get(0));
        }
        for(int i = 0; i < 4; i++){
            assemblyEmulator.next();
            List<String> callStack = assemblyEmulator.getCallStack();
            Assertions.assertEquals(2, callStack.size());
            Assertions.assertEquals("MAIN", callStack.get(0));
            Assertions.assertEquals("ADD", callStack.get(1));
        }
        for(int i = 0; i < 4; i++){
            assemblyEmulator.next();
            List<String> callStack = assemblyEmulator.getCallStack();
            Assertions.assertEquals(1, callStack.size());
            Assertions.assertEquals("MAIN", callStack.get(0));
        }
    }
}