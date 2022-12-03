package ru.job4j.list;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ListUtilsTest {

    private List<Integer> input;

    @BeforeEach
    void setUp() {
        input = new ArrayList<>(Arrays.asList(1, 3));
    }

    @Test
    void whenAddBefore() {
        ListUtils.addBefore(input, 1, 2);
        assertThat(input).hasSize(3).containsSequence(1, 2, 3);
    }

    @Test
    void whenAddBeforeWithInvalidIndex() {
        assertThatThrownBy(() -> ListUtils.addBefore(input, 3, 2))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void whenAddAfter() {
        ListUtils.addAfter(input, 0, 2);
        assertThat(input).hasSize(3).containsSequence(1, 2, 3);
    }

    @Test
    void whenRemoveIfNumIsOdd() {
        ListUtils.addBefore(input, input.size() - 1, 5);
        ListUtils.addBefore(input, input.size() - 1, 6);
        ListUtils.addBefore(input, input.size() - 1, 7);
        ListUtils.addBefore(input, input.size() - 1, 2);
        ListUtils.addBefore(input, input.size() - 1, 4);
        ListUtils.addBefore(input, input.size() - 1, 11);
        ListUtils.addBefore(input, input.size() - 1, 9);
        assertThat(input).hasSize(9).containsSequence(1, 5, 6, 7, 2, 4, 11, 9, 3);
        ListUtils.removeIf(input, num -> num % 2 != 0);
        assertThat(input).hasSize(3).containsSequence(6, 2, 4);
    }

    @Test
    void whenReplaceIfNumIsNegative() {
        ListUtils.addAfter(input, 0, 2);
        ListUtils.addAfter(input, 0, -1);
        ListUtils.addAfter(input, 0, -3);
        ListUtils.addAfter(input, 0, 4);
        ListUtils.addAfter(input, 0, -5);
        ListUtils.addAfter(input, 0, 6);
        assertThat(input).hasSize(8).containsSequence(1, 6, -5, 4, -3, -1, 2, 3);
        ListUtils.replaceIf(input, num -> num < 0, 0);
        assertThat(input).hasSize(8).containsSequence(1, 6, 0, 4, 0, 0, 2, 3);
    }

    @Test
    void whenRemoveAll() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
        List<Integer> elements = Arrays.asList(7, 4, 1);
        ListUtils.removeAll(list, elements);
        assertThat(list).hasSize(4).containsSequence(2, 3, 5, 6);
    }
}