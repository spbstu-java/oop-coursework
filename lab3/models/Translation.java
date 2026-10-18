package lab3.models;

import lab3.exceptions.InvalidFileFormatException;

public record Translation(String word, String translation)
{
    public static Translation parse(String line)
            throws InvalidFileFormatException
    {
        int first = line.indexOf('|');

        if (first == -1 || first != line.lastIndexOf('|'))
        {
            throw new InvalidFileFormatException("Должен быть только 1 разделитель '|': " + line);
        }

        var left = line.substring(0, first).trim();

        if (left.isBlank())
        {
            throw new InvalidFileFormatException("Пустая часть до разделителя запрещена: " + line);
        }

        var right = line.substring(first + 1).trim();

        if (right.isBlank())
        {
            throw new InvalidFileFormatException("Пустая часть после разделителя запрещена: " + line);
        }

        return new Translation(Words.normalize(left), right);
    }
}
