package lab3;

import lab3.exceptions.FileReadException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public final class FileManager
{
    public static String read(String fileName) throws FileReadException
    {
        try
        {
            var text = Files.readString(Path.of(fileName), StandardCharsets.UTF_8);
            return text.startsWith("\uFEFF") ? text.substring(1) : text;
        }
        catch (InvalidPathException ex)
        {
            throw new FileReadException("Некорректный путь к файлу: " + fileName, ex);
        }
        catch (NoSuchFileException ex)
        {
            throw new FileReadException("Файл не найден: " + fileName, ex);
        }
        catch (AccessDeniedException ex)
        {
            throw new FileReadException("Нет доступа к файлу: " + fileName, ex);
        }
        catch (IOException ex)
        {
            throw new FileReadException("Ошибка чтения файла: " + ex.getMessage(), ex);
        }
    }
}
