import java.util.Arrays;
import java.util.List;

public final class InputParser
{
    private InputParser() { }

    public static List<Integer> integers(String text)
    {
        if (text.isBlank()) return List.of();
        try
        {
            return Arrays.stream(text.strip().split("[\\s,;]+"))
                    .map(Integer::parseInt).toList();
        } catch (NumberFormatException e)
        {
            throw new IllegalArgumentException("Введите целые числа через пробел, запятую или ;", e);
        }
    }

    public static List<String> strings(String text)
    {
        if (text.isEmpty()) return List.of();
        return Arrays.asList(text.split("\\R", -1));
    }

    public static double nonNegativeDecimal(String text, String field)
    {
        try
        {
            double value = Double.parseDouble(text.strip().replace(',', '.'));
            if (!Double.isFinite(value) || value < 0) throw new NumberFormatException();
            return value;
        } catch (NumberFormatException e)
        {
            throw new IllegalArgumentException(field + ": введите конечное неотрицательное число", e);
        }
    }
}
