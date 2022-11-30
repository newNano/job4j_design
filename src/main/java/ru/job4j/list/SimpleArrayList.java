package ru.job4j.list;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class SimpleArrayList<T> implements SimpleList<T> {

    private T[] container;

    private int size;

    private int modCount;

    public SimpleArrayList(int capacity) {
        this.container = (T[]) new Object[capacity];
    }

    @Override
    public void add(T value) {
        modCount++;
        if (size == container.length) {
            grow();
        }
        container[size++] = value;
    }

    @Override
    public T set(int index, T newValue) {
        int i = Objects.checkIndex(index, size);
        T oldValue = container[i];
        container[i] = newValue;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        modCount++;
        int i = Objects.checkIndex(index, size);
        T removedValue = container[i];
        System.arraycopy(container,
                        i + 1,
                        container,
                        i,
                        container.length - index - 1);
        container[container.length - 1] = null;
        size--;
        return removedValue;
    }

    @Override
    public T get(int index) {
        int i = Objects.checkIndex(index, size);
        return container[i];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private final int expectedModCount = modCount;
            private int index;

            @Override
            public boolean hasNext() {
                if (modCount != expectedModCount) {
                    throw new ConcurrentModificationException();
                }
                return index < size;
            }

            @Override
            public T next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                return container[index++];
            }
        };
    }

    private void grow() {
        container = Arrays.copyOf(container, container.length * 2 + 1);
    }
}