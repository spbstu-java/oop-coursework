package task3.translator;

import task3.exception.FileReadException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

// Читает файл с текстом, который нужно перевести
public final class TextFileReader {

    private TextFileReader() {
    }

    public static String read(Path path) throws FileReadException {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            throw new FileReadException("Failed to read text file: " + path, e);
        }
    }
}