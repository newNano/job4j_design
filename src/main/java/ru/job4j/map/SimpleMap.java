package ru.job4j.map;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleMap<K, V> implements Map<K, V> {

    private static final float LOAD_FACTOR = 0.75f;
    private int capacity = 8;
    private int size = 0;
    private int modCount = 0;
    private MapEntry<K, V>[] table = new MapEntry[capacity];

    @Override
    public boolean put(K key, V value) {
        int hash = (key == null) ? hash(0) : hash(key.hashCode());
        int bucket = indexFor(hash);
        if (table[bucket] != null) {
            return false;
        }
        table[bucket] = new MapEntry<>(key, value);
        size++;
        modCount++;
        if (size >= capacity * LOAD_FACTOR) {
            expand();
        }
        return true;
    }

    private int hash(int hashCode) {
        return hashCode ^ (hashCode >>> 16);
    }

    private int indexFor(int hash) {
        return (capacity - 1) & hash;
    }

    private void expand() {
        capacity = table.length * 2;
        MapEntry<K, V>[] newTable = new MapEntry[capacity];
        for (MapEntry<K, V> entry : table) {
            if (entry != null) {
                K k = entry.key;
                int hash = (k == null) ? hash(0) : hash(k.hashCode());
                int bucket = indexFor(hash);
                newTable[bucket] = entry;
            }
        }
        table = newTable;
    }

    @Override
    public V get(K key) {
        V value = null;
        int hash = (key == null) ? hash(0) : hash(key.hashCode());
        int bucket = indexFor(hash);
        MapEntry<K, V> entry = table[bucket];
        if (entry != null) {
            K keyEntry = entry.key;
            int hashEntry = (keyEntry == null) ? hash(0) : hash(keyEntry.hashCode());
            if (hashEntry == hash
                    && (key == keyEntry || (key != null && key.equals(keyEntry)))) {
                value = entry.value;
            }
        }
        return value;
    }

    @Override
    public boolean remove(K key) {
        boolean rsl = false;
        int hash = (key == null) ? hash(0) : hash(key.hashCode());
        int bucket = indexFor(hash);
        if (table[bucket] != null) {
            table[bucket] = null;
            rsl = true;
            size--;
            modCount++;
        }
        return rsl;
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<K>() {
            private final int expectedModCount = modCount;
            private int index;

            @Override
            public boolean hasNext() {
                if (modCount != expectedModCount) {
                    throw new ConcurrentModificationException();
                }
                while (index < table.length && table[index] == null) {
                    index++;
                }
                return index < table.length;
            }

            @Override
            public K next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                return table[index++].key;
            }
        };
    }

    private static class MapEntry<K, V> {
        private K key;
        private V value;

        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}