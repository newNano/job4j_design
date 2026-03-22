package ru.job4j.io;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class ConfigTest {

    @Test
    void whenPairWithoutComment() {
        String path = "data/pair_without_comment.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("name")).isEqualTo("Petr Arsentev");
    }

    @Test
    void whenFileHasCommentsAndEmptyLines() {
        String path = "data/with_comments_and_empty_lines.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("name")).isEqualTo("Petr Arsentev");
        assertThat(config.value("age")).isEqualTo("40");
    }

    @Test
    void whenFileHasInvalidFormatThenThrowIllegalArgumentException() {
        String path = "data/invalid.properties";
        Config config = new Config(path);

        assertThatThrownBy(config::load)
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenValueContainsDelimiterThenParseCorrectly() {
        String path = "data/value_with_delimiter.properties";
        Config config = new Config(path);
        config.load();

        assertThat(config.value("name")).isEqualTo("Petr Arsentev=1");
        assertThat(config.value("age")).isEqualTo("40=");
    }
}