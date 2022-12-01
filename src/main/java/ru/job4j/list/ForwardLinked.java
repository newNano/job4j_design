package ru.job4j.list;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ForwardLinked<T> implements Iterable<T> {

    private int size = 0;
    private int modCount = 0;
    private Node<T> head;

    public void add(T value) {
        if (head == null) {
            head = new Node<>(value, null);
        } else {
            Node<T> point = head;
            while (point.next != null) {
                point = point.next;
            }
            point.next = new Node<>(value, null);
        }
        size++;
        modCount++;
    }

    public T get(int index) {
        Node<T> point = head;
        Objects.checkIndex(index, size);
        int currentIndex = 0;
        while (currentIndex++ != index) {
            point = point.next;
        }
        return point.item;
    }

    public T deleteFirst() {
        if (head == null) {
            throw new NoSuchElementException();
        }
        Node<T> temp = head;
        T deletedValue = head.item;
        Node<T> next = head.next;
        temp.next = null;
        head = next;
        return deletedValue;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private final int expectedModCount = modCount;
            private Node<T> point = head;

            @Override
            public boolean hasNext() {
                if (modCount != expectedModCount) {
                    throw new ConcurrentModificationException();
                }
                return point != null;
            }

            @Override
            public T next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                T value = point.item;
                point = point.next;
                return value;
            }
        };
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;

        Node(T element, Node<T> next) {
            this.item = element;
            this.next = next;
        }
    }
}