package ru.job4j.map;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class SimpleMap<K, V> implements Map<K, V> {

    private static final float LOAD_FACTOR = 0.75f;
    private int capacity = 8;
    private int size = 0;
    private int modCount = 0;
    private MapEntry<K, V>[] table = new MapEntry[capacity];

    @Override
    public boolean put(K key, V value) {
        boolean result = true;
        int bucket = getBucket(key);
        if (table[bucket] != null) {
            result = false;
        } else {
            changeSize(new MapEntry<>(key, value), bucket, 1);
            if (size >= capacity * LOAD_FACTOR) {
                expand();
            }
        }
        return result;
    }

    @Override
    public boolean remove(K key) {
        boolean rsl = false;
        int hash = hash(Objects.hashCode(key));
        int bucket = indexFor(hash);
        MapEntry<K, V> entry = table[bucket];
        if (entry != null
            && hash(Objects.hashCode(entry.key)) == hash(Objects.hashCode(key))
            && Objects.equals(entry.key, key)) {
            changeSize(null, bucket, -1);
            rsl = true;
        }
        return rsl;
    }

    @Override
    public V get(K key) {
        V value = null;
        int hash = hash(Objects.hashCode(key));
        int bucket = indexFor(hash);
        MapEntry<K, V> entry = table[bucket];
        if (entry != null) {
            K keyEntry = entry.key;
            int hashEntry = hash(Objects.hashCode(keyEntry));
            if (hashEntry == hash && Objects.equals(key, keyEntry)) {
                value = entry.value;
            }
        }
        return value;
    }

    private void changeSize(MapEntry<K, V> entry, int bucket, int c) {
        table[bucket] = entry;
        size = size + c;
        modCount++;
    }

    private int getBucket(K key) {
        return indexFor(hash(Objects.hashCode(key)));
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
                int bucket = getBucket(entry.key);
                newTable[bucket] = entry;
            }
        }
        table = newTable;
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