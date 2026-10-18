package lab3.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trie
{
    private final Node root = new Node();

    public void insert(Translation t)
    {
        var node = root;

        var words = t.word().toLowerCase().split(" ");

        for (var word : words)
        {
            node = node.children.computeIfAbsent(word, _ -> new Node());
        }

        node.translation = t.translation();
    }

    public void clear()
    {
        root.children.clear();
    }

    public Match findLongest(List<String> words, int from)
    {
        var node = root;
        var bestWordCount = 0;
        String bestTranslation = null;

        var wordCount = 0;

        for (int i = from; i < words.size(); i++)
        {
            var word = words.get(i);

            if (Words.isWord(word))
            {
                wordCount++;
                node = node.children.get(word.toLowerCase());
                if (node == null)
                {
                    break;
                }

                if (node.translation != null)
                {
                    bestTranslation = node.translation;
                    bestWordCount = wordCount;
                }
            }
            else if (!word.isBlank())
            {
                break;
            }
        }

        return bestTranslation == null
                ? null
                : new Match(bestTranslation, bestWordCount);
    }

    private static class Node
    {
        final Map<String, Node> children = new HashMap<>();
        String translation;
    }
}
