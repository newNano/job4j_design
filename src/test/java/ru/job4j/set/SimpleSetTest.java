package ru.job4j.set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

class SimpleSetTest {

    private Set<String> mySet;

    @BeforeEach
    void init() {
        mySet = new SimpleSet<>();
        mySet.add("book");
        mySet.add("window");
        mySet.add("cat");
        mySet.add("sun");
        mySet.add("snow");
        mySet.add("forest");
    }

    @Test
    void whenAddNonNull() {
        Set<Integer> set = new SimpleSet<>();
        assertThat(set.add(1)).isTrue();
        assertThat(set.contains(1)).isTrue();
        assertThat(set.add(1)).isFalse();
    }

    @Test
    void whenAddNull() {
        Set<Integer> set = new SimpleSet<>();
        assertThat(set.add(null)).isTrue();
        assertThat(set.contains(null)).isTrue();
        assertThat(set.add(null)).isFalse();
    }

    @Test
    void whenAddSomeElems() {
        mySet.add("sun");
        mySet.add("sun");
        mySet.add("cat");
        assertThat(mySet)
                .hasSize(6)
                .containsSequence("book", "window", "cat", "sun", "snow", "forest");
    }

    @Test
    void whenElemsContainsInSet() {
        mySet.add("book");
        mySet.add("window");
        assertThat(mySet.contains("book")).isTrue();
        assertThat(mySet.contains("window")).isTrue();
        assertThat(mySet).hasSize(6);
    }

    @Test
    void whenElemNoContainsInSet() {
        assertThat(mySet.contains("java")).isFalse();
        mySet.add("java");
        assertThat(mySet).hasSize(7);
    }
}