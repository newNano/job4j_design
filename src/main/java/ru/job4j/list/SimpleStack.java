package ru.job4j.list;

public class SimpleStack<T> {
    private ForwardLinked<T> linked = new ForwardLinked<T>();
    private int size;

    public T pop() {
        size--;
        return linked.deleteFirst();
    }

    public void push(T value) {
        size++;
        linked.addFirst(value);
    }

    public boolean isEmpty() {
        return size == 0;
    }
}