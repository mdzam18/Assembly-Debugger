package src.Emulator.Files;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

//import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

class FilesManagerTest {

    @Test
    public void createList() throws Exception {
        FilesManager filesManager = new FilesManager();
        String [] args = new String[2];
        args[0] = "src/Tests/testFiles1.asm";
        args[1] = "src/Tests/testFiles2.asm";
        List<String> list = filesManager.createList(args);
        assertEquals("SP=SP-4;", list.get(0));
        assertEquals("FUNCTIONMAIN;", list.get(1));
        assertEquals("RET;", list.get(2));
        assertEquals(3, list.size());
    }

    @Test
    public void testFileWithoutMain(){
        FilesManager filesManager = new FilesManager();
        String [] args = new String[1];
        args[0] = "src/Tests/testFiles1.asm";
        Throwable exception = assertThrows(Exception.class, () -> filesManager.createList(args));
        Assertions.assertEquals("missing main", exception.getMessage());
    }

    @Test
    public void testFileWithoutSemicolon(){
        FilesManager filesManager = new FilesManager();
        String [] args = new String[1];
        args[0] = "src/Tests/testCommandWithoutSemicolon.asm";
        Throwable exception = assertThrows(Exception.class, () -> filesManager.createList(args));
        Assertions.assertEquals("SP=SP+3 missing semicolon", exception.getMessage());
    }
}