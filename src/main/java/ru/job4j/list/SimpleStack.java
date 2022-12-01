package ru.job4j.list;

import java.util.NoSuchElementException;

public class SimpleStack<T> {
    private ForwardLinked<T> linked = new ForwardLinked<T>();
    private int size;

    public T pop() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        T deletedValue = linked.deleteFirst();
        size--;
        return deletedValue;
    }

    public void push(T value) {
        linked.addFirst(value);
        size++;
    }
}