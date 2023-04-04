package service;

import service.impl.CustomNodeImpl;

public interface CustomNode<K, V> {
    K getKey();

    V getValue();

    V setValue(V value);

    CustomNode<K, V> getNext();

    int getHash();

    void setNext(CustomNodeImpl<K, V> next);
}
