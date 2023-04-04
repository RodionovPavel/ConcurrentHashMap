package service;

public interface CustomHashMap<K, V> {
    void put(K key, V value);

    boolean remove(K key);

    V get(K key);

    boolean containsKey(K key);

    int size();

    void display();

    void clear();

    boolean isEmpty();
}
