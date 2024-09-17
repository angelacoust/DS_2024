package e1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StringGamesTest {

    /**
     * bestCharacters example:
     *
     * "ABC123" and "a12345" are two strings of length 6, so they can compete
     *
     * "ABC123" has: 0 lowercase, 3 uppercase and 3 digits
     * "a12345" has: 1 lowercase, 0 uppercase and 5 digits
     *
     * "ABC123" wins in 1 category (uppercase)
     * "a12345" wins in 2 categories (lowercase and digits)
     *
     * "a12345" wins, and is returned
     */
    @Test
    void bestCharactersTest() {
        assertEquals("a12345", StringGames.bestCharacters("ABC123", "a12345"));
        assertEquals("IAlwaysWin123", StringGames.bestCharacters("IAlwaysWin123","ilostyetagain"));
        assertEquals("aB0", StringGames.bestCharacters("aaa", "aB0"));
        assertEquals("XyZ", StringGames.bestCharacters("XyZ", "123"));
        assertEquals("aaa", StringGames.bestCharacters("aaa", "bbb"));
        assertThrows(IllegalArgumentException.class, () -> StringGames.bestCharacters("abc", "ab"));
    }

    /**
     * crossingWords example:
     * "abc" and "bcd" have 2 combinations
     *
     * 1.     |  2.
     * a b c  |      b
     *   c    |  a b c
     *   d    |      d
     */
    @Test
    void crossingWordsTest() {
        assertEquals(2, StringGames.crossingWords("abc", "bcd"));
        assertEquals(4, StringGames.crossingWords("abcd", "abcd"));
        assertEquals(9, StringGames.crossingWords("zzz", "zzz"));
        assertEquals(0, StringGames.crossingWords("house", "lack"));
        assertEquals(3, StringGames.crossingWords("house", "bongos"));
    }

    /**
     * wackyAlphabet example:
     *
     * wackyAlphabet("hello", "zyxwvutsrqponmlkjihgfedcba") must return "ollhe"
     */
    @Test
    void wackyAlphabetTest() {
        assertEquals("ollhe", StringGames.wackyAlphabet("hello", "zyxwvutsrqponmlkjihgfedcba"));
        assertEquals("hello", StringGames.wackyAlphabet("hello", "abcdfgijkmnhelopqrtsuvwxyz"));
        assertEquals("aaegnrrty", StringGames.wackyAlphabet("targaryen", "aeioubcdfghjklmnpqrstvwxyz"));
        assertEquals("eioghhrtw", StringGames.wackyAlphabet("hightower", "aeioubcdfghjklmnpqrstvwxyz"));
        assertEquals("ytrrngeaa", StringGames.wackyAlphabet("targaryen", "zyxwvutsrqponmlkjihgfedcba"));
        assertThrows(IllegalArgumentException.class, () -> StringGames.wackyAlphabet("allas", "abcdafghajklmnapqrtsavwxyz"));
        assertThrows(IllegalArgumentException.class, () -> StringGames.wackyAlphabet("onlyas", "aaaaaaaaaaaaaaaaaaaaaaaaaa"));
        assertThrows(IllegalArgumentException.class, () -> StringGames.wackyAlphabet("novowels", "bcdfgjkmnhlpqrtsvwxyz"));
        assertThrows(IllegalArgumentException.class, () -> StringGames.wackyAlphabet("numbers", "0bcd1fgjkmn2hl3pqrts4vwxyz"));
    }
}