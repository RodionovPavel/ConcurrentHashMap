package service.impl;

import service.CustomHashMap;
import service.CustomNode;
import utils.HashUtils;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CustomHashMapImpl<K, V> implements CustomHashMap<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;
    private final double loadFactor;
    private int arrayLength;
    private int size;
    private CustomNode<K, V>[] buckets;
    ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public CustomHashMapImpl() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public CustomHashMapImpl(int arrayLength, double loadFactor) {
        this.arrayLength = arrayLength;
        this.loadFactor = loadFactor;
        buckets = new CustomNode[arrayLength];
    }

    @Override
    public void put(K key, V value) {
        readWriteLock.writeLock().lock();

        try {
            if (size + 1 > arrayLength * loadFactor) {
                resize();
            }
            int hashCode = HashUtils.createHash(key);
            int index = indexOfBucket(hashCode, arrayLength);
            CustomNode<K, V> customNode = buckets[index];

            if (buckets[index] == null) {
                buckets[index] = new CustomNodeImpl<>(key, value, hashCode, null);
                size++;
            } else {
                for (CustomNode<K, V> n = customNode; n != null; n = n.getNext()) {

                    if ((key == null && null == n.getKey()) || (key != null && key.equals(n.getKey()))) {
                        n.setValue(value);
                        break;
                    }

                    if (n.getNext() == null) {
                        n.setNext(new CustomNodeImpl<>(key, value, hashCode, null));
                        size++;
                        break;
                    }
                }
            }
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean remove(K key) {
        readWriteLock.writeLock().lock();
        try {
            int hashCode = HashUtils.createHash(key);
            int index = indexOfBucket(hashCode, arrayLength);
            if (buckets[index] == null) {
                return false;
            } else {
                CustomNode<K, V> previous = null;
                CustomNode<K, V> current = buckets[index];

                while (current != null) {
                    if (current.getKey().equals(key)) {
                        if (previous == null) {
                            buckets[index] = buckets[index].getNext();
                        } else {
                            previous.setNext((CustomNodeImpl<K, V>) current.getNext());
                        }
                        size--;
                        return true;
                    }
                    previous = current;
                    current = current.getNext();
                }
            }
            return false;
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public V get(K key) {

        readWriteLock.readLock().lock();
        try {
            int index = indexOfBucket(HashUtils.createHash(key), arrayLength);
            if (buckets[index] != null) {
                CustomNode<K, V> temp = buckets[index];

                for (CustomNode<K, V> n = temp; n != null; n = n.getNext()) {
                    if ((key == null && null == n.getKey()) || (key != null && key.equals(n.getKey()))) {
                        return n.getValue();
                    }
                }
            }
            return null;
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    @Override
    public boolean containsKey(K key) {

        readWriteLock.readLock().lock();
        try {
            int index = indexOfBucket(HashUtils.createHash(key), DEFAULT_CAPACITY);

            if (buckets[index] != null) {
                CustomNode<K, V> temp = buckets[index];

                for (CustomNode<K, V> n = temp; n != null; n = n.getNext()) {
                    if ((key == null && null == n.getKey()) || (key != null && key.equals(n.getKey()))) {
                        return true;
                    }
                }
            }
            return false;

        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        readWriteLock.writeLock().lock();
        try {
            buckets = new CustomNode[arrayLength];
            size = 0;
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public void display() {
        Arrays.stream(buckets).filter(Objects::nonNull).forEach(customNode -> {
            System.out.println("{Key = " + customNode.getKey()
                    + "; Value = " + customNode.getValue() + "}"
                    + " NEXT = " + customNode.getNext() + "; HASH = "
                    + customNode.getHash() + ";");
        });
    }

    private int indexOfBucket(int hashCode, int arrayLength) {
        return Math.abs(hashCode) % arrayLength;
    }

    private void resize() {
        int newArrayLength = arrayLength * 2;
        CustomNode<K, V>[] newArray = new CustomNode[newArrayLength];
        transfer(newArray);
        buckets = newArray;
        arrayLength = newArrayLength;
    }

    private void transfer(CustomNode<K, V>[] newArray) {

        CustomNode<K, V>[] oldArray = buckets;
        for (int j = 0; j < oldArray.length; j++) {
            CustomNode<K, V> oldNode = oldArray[j];
            if (oldNode != null) {
                oldArray[j] = null;
                do {
                    var next = oldNode.getNext();
                    int index = indexOfBucket(HashUtils.createHash(oldNode.getKey()), arrayLength);
                    oldNode.setNext((CustomNodeImpl<K, V>) newArray[index]);
                    newArray[index] = oldNode;
                    oldNode = next;
                } while (oldNode != null);
            }
        }
    }
}
