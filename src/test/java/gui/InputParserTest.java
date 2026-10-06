package gui;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InputParserTest {

    @Test
    void parsesIntegersWithSpaces() {
        assertEquals(List.of(1, -2, 30), InputParser.parseIntegers(" 1, -2 ,30 "));
    }

    @Test
    void blankInputGivesEmptyList() {
        assertTrue(InputParser.parseIntegers("   ").isEmpty());
        assertTrue(InputParser.parseStrings("").isEmpty());
    }

    @Test
    void rejectsNotANumber() {
        assertThrows(IllegalArgumentException.class, () -> InputParser.parseIntegers("1, abc"));
    }

    @Test
    void rejectsEmptyItem() {
        assertThrows(IllegalArgumentException.class, () -> InputParser.parseStrings("a,,b"));
        assertThrows(IllegalArgumentException.class, () -> InputParser.parseStrings("a, b,"));
    }

    @Test
    void parsesStringsAndTrimsThem() {
        assertEquals(List.of("cat", "dog"), InputParser.parseStrings(" cat , dog"));
    }
}