package lab4;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StreamTasks {

    public static double average(List<Integer> numbers) {
        return numbers.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
    }

    public static List<String> upperCaseWithPrefix(List<String> strings) {
        return strings.stream()
                .map(s -> "new" + s.toUpperCase())
                .toList();
    }

    public static List<Integer> squaresOfUnique(List<Integer> numbers) {
        Map<Integer, Long> freq = numbers.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return numbers.stream()
                .filter(n -> freq.get(n) == 1L)
                .map(n -> n * n)
                .toList();
    }

    public static <T> T getLastElement(Collection<T> collection) {
        return collection.stream()
                .reduce((first, second) -> second)
                .orElseThrow(() -> new NoSuchElementException("Коллекция пуста"));
    }

    public static int sumOfEven(int[] numbers) {
        return Arrays.stream(numbers)
                .filter(num -> num % 2 == 0)
                .sum();
    }

    public static Map<Character, String> toMapByFirstChar(List<String> strings) {
        return strings.stream()
                .filter(s -> s != null && !s.isEmpty())
                .collect(Collectors.toMap(
                        s -> s.charAt(0),
                        s -> s.substring(1),
                        (existing, replacement) -> replacement,
                        LinkedHashMap::new
                ));
    }
}
