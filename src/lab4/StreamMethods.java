package lab4;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.*;

public class StreamMethods {

    // 1. Возвращает среднее значение списка целых чисел
    public static double average(List<Integer> numbers) {
        return numbers.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0);
    }

    // 2. Переводит строки в верхний регистр и добавляет префикс "*new*"
    public static List<String> addPrefix(List<String> strings) {
        return strings.stream()
                .map(s -> "*new*" + s.toUpperCase())
                .collect(Collectors.toList());
    }

    // 3. Возвращает квадраты элементов, встречающихся только один раз
    public static List<Integer> uniqueSquares(List<Integer> numbers) {
        Map<Integer, Long> counts = numbers.stream()
                .collect(Collectors.groupingBy(
                        n -> n,
                        Collectors.counting()
                ));

        return numbers.stream()
                .filter(n -> counts.get(n) == 1)
                .map(n -> n * n)
                .collect(Collectors.toList());
    }

    // 4. Возвращает последний элемент коллекции или выбрасывает исключение, если коллекция пуста
    public static <T> T lastElement(Collection<T> collection) {
        return collection.stream()
                .reduce((first, second) -> second)
                .orElseThrow(() ->
                        new NoSuchElementException("Коллекция пуста")
                );
    }

    // 5. Возвращает сумму чётных чисел массива (если чётных чисел нет, возвращает 0)
    public static int sumEven(int[] numbers) {
        return Arrays.stream(numbers).filter(n -> n % 2 == 0).sum();
    }

    // 6. Преобразует строки в Map: первый символ — ключ, остальные символы — значение
    public static Map<Character, String> toMap(List<String> strings) {
        return strings.stream()
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toMap(
                        s -> s.charAt(0),
                        s -> s.substring(1),
                        (oldValue, newValue) -> newValue,
                        LinkedHashMap::new
                ));
    }

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(2, 3, 4, 3, 5, 16);
        List<String> strings = Arrays.asList("apple", "orange", "banana", "grape", "pineapple");

        System.out.println("1. Среднее значение: " + average(numbers));
        System.out.println("2. Строки с префиксом: " + addPrefix(strings));
        System.out.println("3. Квадраты уникальных элементов: " + uniqueSquares(numbers));

        try {
            System.out.println("4. Последний элемент: " + lastElement(numbers));
        } catch (NoSuchElementException e) {
            System.out.println("4. Ошибка: " + e.getMessage());
        }

        int[] array = {1, 2, 3, 4, 6};
        System.out.println("5. Сумма чётных чисел: " + sumEven(array));
        System.out.println("6. Map из строк: " + toMap(strings));
    }
}
