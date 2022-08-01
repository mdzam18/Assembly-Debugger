package src.Emulator.Branches;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import src.Emulator.Address.AddressManager;
import src.Emulator.Memory.MemoryManager;
import src.Emulator.Registers.RegistersManager;

import java.lang.reflect.Member;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

class BranchesManagerTest {

    @Test
    public void branches() throws Exception {
        RegistersManager registersManager = new RegistersManager();
        registersManager.addRegister("R2", 3);
        BranchesManager branches = new BranchesManager(registersManager);
        Assertions.assertEquals(3, branches.branches("BGT5,4,PC+12;", 0));
        Assertions.assertEquals(-1, branches.branches("BGT3,4,PC+12;", 0));

        Assertions.assertEquals(3, branches.branches("BLT3,4,PC+12;", 0));
        Assertions.assertEquals(-1, branches.branches("BLT5,4,PC+12;", 0));

        Assertions.assertEquals(3, branches.branches("BEQ4,4,PC+12;", 0));
        Assertions.assertEquals(-1, branches.branches("BEQ5,4,PC+12;", 0));

        Assertions.assertEquals(3, branches.branches("BNE5,4,PC+12;", 0));
        Assertions.assertEquals(-1, branches.branches("BNE4,4,PC+12;", 0));

        Assertions.assertEquals(3, branches.branches("BGE5,4,PC+12;", 0));
        Assertions.assertEquals(-1, branches.branches("BGE3,4,PC+12;", 0));

        Assertions.assertEquals(3, branches.branches("BLE3,4,PC+12;", 0));
        Assertions.assertEquals(-1, branches.branches("BLE5,4,PC+12;", 0));

        Throwable exception = assertThrows(Exception.class, () -> branches.branches("BLE;", 0));
        Assertions.assertEquals("missing values to compare", exception.getMessage());

        exception = assertThrows(Exception.class, () -> branches.branches("BLE2,PC+4;", 0));
        Assertions.assertEquals("missing second value", exception.getMessage());

        exception = assertThrows(Exception.class, () -> branches.branches("BLE2,3;", 0));
        Assertions.assertEquals("missing pc", exception.getMessage());

        exception = assertThrows(Exception.class, () -> branches.branches("BLEM[sp],2,pc+12;", 0));
        Assertions.assertEquals("invalid operation", exception.getMessage());

        exception = assertThrows(Exception.class, () -> branches.branches("BLER1,2,pc+12;", 0));
        Assertions.assertEquals("does not contain register: R1", exception.getMessage());

        Assertions.assertEquals(3, branches.branches("BGE3,R2,PC+12;", 0));
        Assertions.assertEquals(-1, branches.branches("BGE1,R2,PC+12;", 0));
    }

    @Test
    public void jump() throws Exception {
        RegistersManager registersManager = new RegistersManager();
        BranchesManager branchesManager = new BranchesManager(registersManager);
        Assertions.assertEquals(5, branchesManager.jump("JMPPC-24;", 10));
    }

    @Test
    public void asserts() throws Exception {
        RegistersManager registersManager = new RegistersManager();
        registersManager.setRv(2);
        BranchesManager branches = new BranchesManager(registersManager);
        Assertions.assertEquals("name PASSED", branches.asserts("AGT5,4;", "functionname"));
        String expected = "name FAILED\n";
        expected = expected + "Expected greater than: 4 Got: 3";
        Assertions.assertEquals(expected, branches.asserts("AGT3,4;", "functionname"));

        Assertions.assertEquals("name PASSED", branches.asserts("ALT3,4;", "functionname"));
        expected = "name FAILED\n";
        expected = expected + "Expected less than: 4 Got: 5";
        Assertions.assertEquals(expected, branches.asserts("ALT5,4;", "functionname"));

        Assertions.assertEquals("name PASSED", branches.asserts("AEQ4,4;", "functionname"));
        expected = "name FAILED\n";
        expected = expected + "Expected: 4 Got: 5";
        Assertions.assertEquals(expected, branches.asserts("AEQ5,4;", "functionname"));

        Assertions.assertEquals("name PASSED", branches.asserts("ANE5,4;", "functionname"));
        expected = "name FAILED\n";
        expected = expected + "Expected not equal: 4 Got: 4";
        Assertions.assertEquals(expected, branches.asserts("ANE4,4;", "functionname"));

        Assertions.assertEquals("name PASSED", branches.asserts("AGE5,4;", "functionname"));
        expected = "name FAILED\n";
        expected = expected + "Expected greater or equal than: 4 Got: 3";
        Assertions.assertEquals(expected, branches.asserts("AGE3,4;", "functionname"));

        Assertions.assertEquals("name PASSED", branches.asserts("ALE3,4;", "functionname"));
        expected = "name FAILED\n";
        expected = expected + "Expected less or equal than: 4 Got: 5";
        Assertions.assertEquals(expected, branches.asserts("ALE5,4;", "functionname"));

        Assertions.assertEquals("name FAILED\n", branches.asserts("Akl1,2;", "functionname"));
        Assertions.assertEquals("name PASSED", branches.asserts("AGERV,1;", "functionname"));
    }
}