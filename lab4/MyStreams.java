package lab4;

import java.util.*;
import java.util.stream.Collectors;

public class MyStreams
{
    public static double average(List<Integer> numbers) {
        return numbers
                .stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
    }

    public static List<String> upperWithPrefix(List<String> strings) {
        return strings
                .stream()
                .map(s -> "new" + s.toUpperCase())
                .collect(Collectors.toList());
    }

    public static List<Integer> squaresOfUniqueNumbers(List<Integer> numbers) {
        return numbers
                .stream()
                .collect(Collectors.groupingBy(n -> n, LinkedHashMap::new, Collectors.counting()))
                .entrySet()
                .stream()
                .filter(e -> e.getValue() == 1)
                .map(e -> e.getKey() * e.getKey())
                .collect(Collectors.toList());
    }

    public static <T> T lastElementOfCollection(Collection<T> source) {
        return source
                .stream()
                .reduce((_, second) -> second)
                .orElseThrow(() -> new NoSuchElementException("Пустая коллекция. Последнего элемента не существует"));
    }

    public static int sumOfEvenNumbers(int[] numbers) {
        return Arrays
                .stream(numbers)
                .filter(n -> n % 2 == 0)
                .sum();
    }

    public static Map<Character, String> toMapByFirstSymbol(List<String> strings) {
        return strings
                .stream()
                .filter(s -> s != null && !s.isBlank())
                .collect(Collectors.toMap(
                        s -> s.charAt(0),
                        s -> s.substring(1),
                        (exist, newValue) -> exist + "," + newValue,
                        LinkedHashMap::new
                ));
    }
}
