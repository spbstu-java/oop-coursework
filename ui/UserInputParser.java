package ui;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

final class UserInputParser
{
    static List<Integer> integers(String text)
    {
        return Arrays.stream(text.trim().split("[\\s,;]+"))
                .filter(s -> !s.isEmpty())
                .map(UserInputParser::parseInt)
                .collect(Collectors.toList());
    }

    static List<String> strings(String text)
    {
        return Arrays.stream(text.split("[,;]"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    private static int parseInt(String s)
    {
        try
        {
            return Integer.parseInt(s);
        }
        catch (NumberFormatException ex)
        {
            throw new IllegalArgumentException(s + " не является целым числом");
        }
    }
}
