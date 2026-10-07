import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StreamTasks
{

    public static double average(List<Integer> numbers)
    {
        return numbers.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElseThrow(() -> new NoSuchElementException("Нельзя вычислить среднее пустого списка"));
    }

    public static List<String> uppercaseWithPrefix(List<String> strings)
    {
        return strings.stream()
                .map(text -> "*new*" + text.toUpperCase(Locale.ROOT))
                .toList();
    }

    public static List<Long> uniqueSquares(List<Integer> numbers)
    {
        Map<Integer, Long> frequencies = numbers.stream()
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));

        return numbers.stream()
                .filter(number -> frequencies.get(number) == 1L)
                .map(number -> (long) number * number)
                .toList();
    }

    public static <T> T lastElement(Collection<T> collection)
    {
        return collection.stream()
                .reduce((previous, current) -> current)
                .orElseThrow(() -> new NoSuchElementException("Коллекция пуста"));
    }

    public static long sumEven(int[] numbers)
    {
        return Arrays.stream(numbers)
                .filter(number -> number % 2 == 0)
                .asLongStream()
                .sum();
    }

    public static Map<Character, String> stringsToMap(List<String> strings)
    {
        return strings.stream()
                .collect(Collectors.toMap(
                        text -> {
                            if (text.isEmpty())
                            {
                                throw new IllegalArgumentException("Пустая строка не может стать записью Map");
                            }
                            return text.charAt(0);
                        },
                        text -> text.substring(1),
                        (previous, current) -> current,
                        LinkedHashMap::new
                ));
    }

}
