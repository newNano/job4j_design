package ru.job4j.io;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.*;
import java.nio.file.Path;
import static org.assertj.core.api.Assertions.assertThat;

class AnalysisTest {

    @Test
    void unavailable(@TempDir Path tempDir) throws IOException {
        File source = tempDir.resolve("source.txt").toFile();
        File target = tempDir.resolve("target.txt").toFile();
        try (PrintWriter out = new PrintWriter(source)) {
            out.println("200 10:56:01");
            out.println("500 10:57:01");
            out.println("400 10:58:01");
            out.println("200 10:59:01");
            out.println("500 11:01:02");
            out.println("200 11:02:02");
        }
        new Analysis().unavailable(source.getAbsolutePath(), target.getAbsolutePath());

        StringBuilder result = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader(target))) {
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line).append(System.lineSeparator());
            }
        }
        assertThat(result.toString()).isEqualTo(
            "10:57:01;10:59:01;" + System.lineSeparator()
            + "11:01:02;11:02:02;" + System.lineSeparator()
        );
    }
}