package lab3;

import lab3.exceptions.FileReadException;
import lab3.exceptions.InvalidFileFormatException;
import lab3.models.Match;
import lab3.models.Translation;
import lab3.models.Trie;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Glossary
{
    private final Trie vocabulary = new Trie();

    private final String fileName;

    public Glossary(String source)
    {
        fileName = source;
    }

    public void loadVocabulary()
            throws InvalidFileFormatException, FileReadException
    {
        try(var br = new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8)))
        {
            String line;
            int rowNumber = 0;

            while((line=br.readLine())!=null)
            {
                rowNumber++;

                if (line.isBlank())
                {
                    continue;
                }

                try
                {
                    var parsed = Translation.parse(line);
                    vocabulary.clear();
                    vocabulary.insert(parsed);
                }
                catch (InvalidFileFormatException ex)
                {
                    throw new InvalidFileFormatException("Строка " + rowNumber + ": " + ex.getMessage(), ex);
                }
            }
        }
        catch (FileNotFoundException ex)
        {
            throw new FileReadException("Файл не найден: " + fileName, ex);
        }
        catch (SecurityException ex)
        {
            throw new FileReadException("Файл недоступен: " + fileName, ex);
        }
        catch (IOException ex)
        {
            throw new FileReadException("Ошибка чтения файла: " + ex.getMessage(), ex);
        }
    }

    public Match findLongest(List<String> words, int from)
    {
        return vocabulary.findLongest(words, from);
    }
}
