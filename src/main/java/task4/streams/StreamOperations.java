package task4.streams;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.OptionalDouble;
import java.util.function.Function;
import java.util.stream.Collectors;

/** Pure Stream API utility methods; no loops, no mutation of inputs. */
public final class StreamOperations {

    private static final String NEW_PREFIX = "_new_";

    private StreamOperations() {
    }

    /** Average of the given integers; empty if the list is empty. */
    public static OptionalDouble average(List<Integer> numbers) {
        return numbers.stream()
                .mapToInt(Integer::intValue)
                .average();
    }

    /** Upper-cases every string and prepends the literal prefix "_new_". */
    public static List<String> upperCaseWithPrefix(List<String> strings) {
        return strings.stream()
                .map(s -> NEW_PREFIX + s.toUpperCase())
                .collect(Collectors.toList());
    }

    /** Squares of every element that appears exactly once in the list. */
    public static List<Integer> squaresOfUniqueElements(List<Integer> numbers) {
        Map<Integer, Long> counts = numbers.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return counts.entrySet().stream()
                .filter(entry -> entry.getValue() == 1)
                .map(Map.Entry::getKey)
                .map(n -> n * n)
                .collect(Collectors.toList());
    }

    /**
     * Last element of the collection, in iteration order.
     *
     * @throws NoSuchElementException if the collection is empty
     */
    public static <T> T lastElement(Collection<T> collection) {
        return collection.stream()
                .reduce((first, second) -> second)
                .orElseThrow(() -> new NoSuchElementException("Collection is empty"));
    }

    /** Sum of the even numbers in the array, or 0 if there are none. */
    public static int sumOfEvenNumbers(int[] numbers) {
        return Arrays.stream(numbers)
                .filter(n -> n % 2 == 0)
                .sum();
    }

    /**
     * Maps each string's first character to the remainder of the string.
     * Assumes every string is non-empty and first characters are distinct.
     */
    public static Map<Character, String> firstCharacterIndex(List<String> strings) {
        return strings.stream()
                .collect(Collectors.toMap(s -> s.charAt(0), s -> s.substring(1)));
    }
}