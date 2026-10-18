package lab3.models;

public final class Words
{
    private Words() {}

    public static boolean isWord(String token)
    {
        return !token.isBlank() &&
                Character.isLetterOrDigit(token.charAt(0));
    }

    public static boolean isWordChar(char c)
    {
        return Character.isLetterOrDigit(c) || c == '-' || c == '\'';
    }

    public static String normalize(String text)
    {
        return text.trim().replaceAll("\\s+", " ");
    }
}
