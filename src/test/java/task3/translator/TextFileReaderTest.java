package task3.translator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import task3.exception.FileReadException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TextFileReaderTest {

    @TempDir
    Path tempDir;

    @Test
    void readsFileContent() throws IOException, FileReadException {
        Path file = tempDir.resolve("text.txt");
        Files.writeString(file, "dog look");

        assertEquals("dog look", TextFileReader.read(file));
    }

    @Test
    void throwsWhenFileDoesNotExist() {
        Path missing = tempDir.resolve("missing.txt");

        assertThrows(FileReadException.class, () -> TextFileReader.read(missing));
    }
}