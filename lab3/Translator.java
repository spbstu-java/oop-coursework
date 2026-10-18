package lab3;

import lab3.models.Words;
import java.util.ArrayList;
import java.util.List;

public class Translator
{
    private final Glossary glossary;

    public Translator(Glossary glossary)
    {
        this.glossary = glossary;
    }


    public String multilineTranslate(String text)
    {
        var lines = text.split("\\R", -1);
        var result = new StringBuilder();

        for (var i = 0; i < lines.length; i++)
        {
            if (i > 0)
            {
                result.append('\n');
            }
            result.append(translate(lines[i]));
        }

        return result.toString();
    }

    public String translate(String text)
    {
        var normalized = Words.normalize(text);
        var words = toSegment(normalized);

        var builder = new StringBuilder();
        var i = 0;

        while (i < words.size())
        {
            if (!Words.isWord(words.get(i)))
            {
                builder.append(words.get(i));
                i++;
                continue;
            }

            var match = glossary.findLongest(words, i);

            if (match != null)
            {
                builder.append(match.translation());
                i = skipWords(words, i, match.wordCount());
            }
            else
            {
                builder.append(words.get(i));
                i++;
            }
        }

        return builder.toString();
    }

    private static int skipWords(List<String> words, int from, int count)
    {
        var i = from;
        var seen = 0;

        while (i < words.size() && seen < count)
        {
            if (Words.isWord(words.get(i)))
            {
                seen++;
            }
            i++;
        }

        return i;
    }

    private static List<String> toSegment(String text)
    {
        var words = new ArrayList<String>();
        var builder = new StringBuilder();

        for (char c : text.toCharArray())
        {
            if (Words.isWordChar(c))
            {
                builder.append(c);
            }
            else
            {
                if (!builder.isEmpty())
                {
                    words.add(builder.toString());
                    builder.setLength(0);
                }
                words.add(String.valueOf(c));
            }
        }

        if (!builder.isEmpty())
        {
            words.add(builder.toString());
        }

        return words;
    }
}
