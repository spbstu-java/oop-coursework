package stream;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class StreamMethods {

    public static double average(List<Integer> numbers) {
        return numbers.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0);
    }

    public static List<String> upperWithPrefix(List<String> strings) {
        return strings.stream()
                .map(s -> "_new_" + s.toUpperCase())
                .collect(Collectors.toList());
    }

    public static List<Integer> uniqueSquares(List<Integer> numbers) {
        Map<Integer, Long> counts = numbers.stream()
                .collect(Collectors.groupingBy(n -> n, Collectors.counting()));

        return numbers.stream()
                .filter(n -> counts.get(n) == 1)
                .map(n -> n * n)
                .collect(Collectors.toList());
    }

    public static <T> T lastElement(Collection<T> collection) {
        return collection.stream()
                .reduce((first, second) -> second)
                .orElseThrow(() -> new NoSuchElementException("коллекция пуста"));
    }

    public static int sumEven(int[] numbers) {
        return Arrays.stream(numbers)
                .filter(n -> n % 2 == 0)
                .sum();
    }

    public static Map<Character, String> toMap(List<String> strings) {
        return strings.stream()
                .collect(Collectors.toMap(
                        s -> s.charAt(0),
                        s -> s.substring(1),
                        (a, b) -> a
                ));
    }
}
