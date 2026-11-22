package streams;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Locale; // чтобы не зависело от языка системы
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;
import java.util.function.Predicate;


public final class StreamOps {
    private static final String NEW_PREFIX = "_new_";

    private StreamOps() {
    }

    public static double averageOf(List<Integer> numbers) {
        return numbers.stream()
                .mapToDouble(Integer::doubleValue)
                .average()
                .orElseThrow(() -> new IllegalArgumentException("Нельзя посчитать среднее пустого списка"));
    }

    public static List<String> upperWithPrefix(List<String> strings) {
        return strings.stream()
                .map(s -> NEW_PREFIX + s.toUpperCase(Locale.ROOT))
                .toList();
    }

    public static List<Long> squaresOfUnique(List<Integer> numbers) {
        return numbers.stream()
                .collect(Collectors.toMap(Function.identity(), n -> true, (seen, again) -> false, LinkedHashMap::new))
                .entrySet().stream()
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey)
                .map(n -> (long) n * n)
                .toList();
    }

    public static <T> T lastElement(Collection<T> items) {
        return items.stream()
                .skip(Math.max(0, items.size() - 1))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Коллекция пуста"));
    }

    public static long evenSum(int[] numbers) {
        return IntStream.of(numbers)
                .filter(n -> n % 2 == 0)
                .asLongStream()
                .sum();
    }

    public static Map<Character, String> indexByFirstChar(List<String> strings) {
        return strings.stream()
                .filter(Predicate.not(String::isEmpty))
                .collect(Collectors.toMap(
                        s -> s.charAt(0),
                        s -> s.substring(1),
                        (first, duplicate) -> first,
                        TreeMap::new));
    }
}
