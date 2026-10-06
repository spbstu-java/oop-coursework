package task4.streams;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.OptionalDouble;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StreamOperationsTest {

    @Test
    void averageComputesMeanOfNonEmptyList() {
        OptionalDouble result = StreamOperations.average(List.of(2, 4, 6));

        assertTrue(result.isPresent());
        assertEquals(4.0, result.getAsDouble());
    }

    @Test
    void averageIsEmptyForEmptyList() {
        assertTrue(StreamOperations.average(List.of()).isEmpty());
    }

    @Test
    void upperCaseWithPrefixTransformsEveryString() {
        List<String> result = StreamOperations.upperCaseWithPrefix(List.of("hello", "world"));

        assertEquals(List.of("_new_HELLO", "_new_WORLD"), result);
    }

    @Test
    void squaresOfUniqueElementsSkipsDuplicates() {
        List<Integer> result = StreamOperations.squaresOfUniqueElements(List.of(1, 2, 2, 3, 3, 3, 4));

        assertEquals(2, result.size());
        assertTrue(result.containsAll(List.of(1, 16)));
    }

    @Test
    void lastElementReturnsTheLastOneInIterationOrder() {
        assertEquals(3, StreamOperations.lastElement(List.of(1, 2, 3)));
    }

    @Test
    void lastElementThrowsOnEmptyCollection() {
        assertThrows(NoSuchElementException.class, () -> StreamOperations.lastElement(List.of()));
    }

    @Test
    void sumOfEvenNumbersSkipsOddOnes() {
        assertEquals(6, StreamOperations.sumOfEvenNumbers(new int[] {1, 2, 3, 4}));
    }

    @Test
    void sumOfEvenNumbersIsZeroWhenNoneAreEven() {
        assertEquals(0, StreamOperations.sumOfEvenNumbers(new int[] {1, 3, 5}));
    }

    @Test
    void firstCharacterIndexMapsFirstCharToRemainder() {
        Map<Character, String> result = StreamOperations.firstCharacterIndex(List.of("cat", "dog"));

        assertEquals("at", result.get('c'));
        assertEquals("og", result.get('d'));
    }
}