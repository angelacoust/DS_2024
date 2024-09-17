package e2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KeyPadTest {
    public static char[][] keyPad1 = {{'1'}};

    public static char[][] keyPad2
            = {{'1', '2'},
            {'3', '4'}};

    public static char[][] keyPad3
            = {{'1', '2', '3'},
            {'4', '5', '6'},
            {'7', '8', '9'}};

    public static char[][] keyPad4
            = {{'1', '2', '3'},
            {'4', '5', '6'},
            {'7', '8', '9'},
            {'0', 'A', 'B'}};

    public static char[][] keyPad5
            = {{'1', '4', '7', '0', 'C'},
            {'2', '5', '8', 'A', 'D'},
            {'3', '6', '9', 'B', 'E'}};

    public static char[][] keyPad6
            = {{'1', '2', '3', '4', '5'}};

    public static char[][] keyPad7
            = {{'1'},
            {'2'},
            {'3'}};

    public static String[] input0 = {"RD", "DRUU", "LLD", "D"};
    public static String[] input1 = {"ULL", "RRDDD", "LURDL", "UUUUD"};
    public static String[] input2 = {"URLLLRD", "LLRRDDUULR", "LLUUDDRR", "LRULRU", "LDRULRDU"};

    @Test
    void isValidKeyPadTest() {
        char[][] wrongDuplicates
                = {{'1', '2', '3'},
                {'4', '5', '6'},
                {'7', '8', '1'}};

        char[][] wrongNotSequence
                = {{'1', '2', '4'},
                {'3', '5', '6'},
                {'8', '9', '7'},
                {'0', 'A', 'B'}};

        char[][] wrongNull
                = {{'1', '2', '4'},
                null,
                {'8', '9', '7'},
                {'0', 'A', 'B'}};

        char[][] wrongNoValidChar
                = {{'1', '2', '3'},
                {'4', 'f', '6'},
                {'7', '8', '1'}};

        char[][] notRectangular
                = {{'1'},
                {'2', '5', '8', 'A', 'D'},
                {'3', '6', '9', 'B', 'E'}};

        char[][] notRectangular2
                = {{'1', '4', '7', '0', 'C'},
                {'2', '5', '8', 'A'},
                {'3', '6', '9', 'B', 'E'}};

        assertFalse(KeyPad.isValidKeyPad(wrongDuplicates));
        assertFalse(KeyPad.isValidKeyPad(wrongNotSequence));
        assertFalse(KeyPad.isValidKeyPad(wrongNull));
        assertFalse(KeyPad.isValidKeyPad(wrongNoValidChar));
        assertFalse(KeyPad.isValidKeyPad(null));
        assertFalse(KeyPad.isValidKeyPad(notRectangular));
        assertFalse(KeyPad.isValidKeyPad(notRectangular2));

        assertTrue(KeyPad.isValidKeyPad(keyPad1));
        assertTrue(KeyPad.isValidKeyPad(keyPad2));
        assertTrue(KeyPad.isValidKeyPad(keyPad3));
        assertTrue(KeyPad.isValidKeyPad(keyPad4));
        assertTrue(KeyPad.isValidKeyPad(keyPad5));
        assertTrue(KeyPad.isValidKeyPad(keyPad6));
        assertTrue(KeyPad.isValidKeyPad(keyPad7));
    }

    @Test
    void areValidMovementsTest() {
        String[] wrongLower = {"ULDD", "LRU", "dDd"};
        String[] wrongNumber = {"ULDD", "L1U", "DDD"};
        String[] wrongEmpty = {"", "L1U", "DDD"};
        String[] wrongNull = {"DUU", null, "DDD"};

        assertFalse(KeyPad.areValidMovements(wrongLower));
        assertFalse(KeyPad.areValidMovements(wrongNumber));
        assertFalse(KeyPad.areValidMovements(wrongEmpty));
        assertFalse(KeyPad.areValidMovements(wrongNull));
        assertFalse(KeyPad.areValidMovements(null));

        assertTrue(KeyPad.areValidMovements(input0));
        assertTrue(KeyPad.areValidMovements(input1));
        assertTrue(KeyPad.areValidMovements(input2));
    }

    @Test
    void obtainCodeTest() {
        assertEquals("5347", KeyPad.obtainCode(keyPad3, input0));
        assertEquals("2311", KeyPad.obtainCode(keyPad6, input0));
        assertEquals("2123", KeyPad.obtainCode(keyPad7, input0));

        assertEquals("1111", KeyPad.obtainCode(keyPad1, input1));
        assertEquals("1433", KeyPad.obtainCode(keyPad2, input1));
        assertEquals("1985", KeyPad.obtainCode(keyPad3, input1));
        assertEquals("1BA5", KeyPad.obtainCode(keyPad4, input1));
        assertEquals("1965", KeyPad.obtainCode(keyPad5, input1));
        assertEquals("1322", KeyPad.obtainCode(keyPad6, input1));
        assertEquals("1332", KeyPad.obtainCode(keyPad7, input1));

        assertEquals("11111", KeyPad.obtainCode(keyPad1, input2));
        assertEquals("42422", KeyPad.obtainCode(keyPad2, input2));
        assertEquals("53933", KeyPad.obtainCode(keyPad3, input2));
        assertEquals("56933", KeyPad.obtainCode(keyPad4, input2));
        assertEquals("57977", KeyPad.obtainCode(keyPad5, input2));
        assertEquals("23333", KeyPad.obtainCode(keyPad6, input2));
        assertEquals("21311", KeyPad.obtainCode(keyPad7, input2));

        char[][] notRectangular
                = {{'1'},
                {'2', '5', '8', 'A', 'D'},
                {'3', '6', '9', 'B', 'E'}};
        String[] wrongLower = {"ULDD", "LRU", "dDd"};
        assertThrows(IllegalArgumentException.class, () -> KeyPad.obtainCode(notRectangular, input1));
        assertThrows(IllegalArgumentException.class, () -> KeyPad.obtainCode(keyPad1, wrongLower));
    }
}
