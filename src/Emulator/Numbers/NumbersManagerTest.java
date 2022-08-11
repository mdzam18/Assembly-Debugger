package src.Emulator.Numbers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumbersManagerTest {

    @Test
    public void testGetAndContainsOperator() {
        NumbersManager numbersManager = new NumbersManager();
        char operator = numbersManager.getOperator("a + b");
        assertTrue(numbersManager.containsOperator("a+b"));
        assertEquals('+', operator);

        operator = numbersManager.getOperator("a-b");
        assertTrue(numbersManager.containsOperator("a-b"));
        assertEquals('-', operator);

        operator = numbersManager.getOperator("a*b");
        assertTrue(numbersManager.containsOperator("a*b"));
        assertEquals('*', operator);

        operator = numbersManager.getOperator("a/b");
        assertTrue(numbersManager.containsOperator("a/b"));
        assertEquals('/', operator);

        operator = numbersManager.getOperator("a");
        assertEquals(0, operator);
    }

    @Test
    public void isNumber() {
        NumbersManager numbersManager = new NumbersManager();
        for(int i = 0 ; i < 100; i++){
            assertTrue(numbersManager.isNumber(String.valueOf(i)));
            assertFalse(numbersManager.isNumber("a" + i));
        }
    }
}