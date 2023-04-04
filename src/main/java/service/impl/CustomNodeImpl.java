package service.impl;

import service.CustomNode;

public class CustomNodeImpl<K, V> implements CustomNode<K, V> {
    final int hash;
    final K key;
    volatile V value;
    CustomNode<K, V> next;

    public CustomNodeImpl(K key, V value, int hash, CustomNodeImpl<K, V> next) {
        this.hash = hash;
        this.key = key;
        this.value = value;
        this.next = next;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        this.value = value;
        return value;
    }

    @Override
    public int getHash() {
        return hash;
    }

    public CustomNode<K, V> getNext() {
        return next;
    }

    @Override
    public void setNext(CustomNodeImpl<K, V> next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "CustomNodeImpl{" +
                "hash=" + hash +
                ", key=" + key +
                ", value=" + value +
                ", next=" + next +
                '}';
    }
}
