package ru.job4j.assertj;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.assertj.core.data.Index;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class SimpleConvertTest {
    @Test
    void checkArray() {
        SimpleConvert simpleConvert = new SimpleConvert();
        String[] resultArray =
                simpleConvert.toArray("first", "second", "three", "four", "five");
        assertThat(resultArray).hasSize(5)
                .contains("second")
                .contains("first", Index.atIndex(0))
                .containsAnyOf("zero", "second", "six")
                .doesNotContain("first", Index.atIndex(1));
    }

    @Test
    void checkList() {
        SimpleConvert simpleConvert = new SimpleConvert();
        List<String> resultList =
                simpleConvert.toList("first", "second", "three", "four", "five", "six");
        assertThat(resultList).isNotEmpty()
                .hasSize(6)
                .contains("four")
                .contains("five", Index.atIndex(4))
                .containsSequence("second", "three")
                .allSatisfy(elem -> {
                    assertThat(elem.length()).isLessThan(7);
                    assertThat(elem.length()).isGreaterThan(2);
                })
                .anySatisfy(elem -> {
                    assertThat(elem).isEqualTo("six");
                    assertThat(elem.length()).isGreaterThan(2);
                })
                .allMatch(elem -> elem.length() > 2)
                .anyMatch(elem -> elem.equals("five"))
                .noneMatch(elem -> elem.equals("seven"))
                .first().isEqualTo("first");
    }

    @Test
    void checkSet() {
        SimpleConvert simpleConvert = new SimpleConvert();
        Set<String> resultSet =
                simpleConvert.toSet("5", "4", "3", "1", "3", "3", "2", "1", "5", "2");
        assertThat(resultSet).hasSize(5);
        assertThat(resultSet).element(2).isEqualTo("3");
        assertThat(resultSet).first().isEqualTo("1");
        assertThat(resultSet).last().isNotNull().isEqualTo("5");
    }

    @Test
    void checkMap() {
        SimpleConvert simpleConvert = new SimpleConvert();
        Map<String, Integer> resultMap =
                simpleConvert.toMap("a", "b", "b", "a", "a", "c", "d", "c");
        assertThat(resultMap)
                .containsKeys("a", "b", "c", "d")
                .containsValues(0, 1, 5, 6)
                .containsEntry("a", 0)
                .containsEntry("b", 1)
                .containsEntry("c", 5)
                .containsEntry("d", 6)
                .hasSize(4);
    }
}