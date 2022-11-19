package ROBOT.Structure.cache;

import java.util.HashMap;
import java.util.Map;

//implemented a Cache in order to save previous sensor Values, in order to later compute the expected value by means
//of extrapolation

public class Cache<K, V> {
    private final int CAPACITY;
    private Map<K, CacheItem> map;
    private CacheItem first, last;
    private int size;

    public Cache(int capacity) {
        CAPACITY = capacity;
        map = new HashMap<>(CAPACITY);
    }

    public void put(K key, V value) {
        CacheItem node = new CacheItem<K, Object>(key, value);

        if (map.containsKey(key) == false) {
            if (size() >= CAPACITY) {
                deleteNode(first);
            }
            addNodeToLast(node);
        }
        map.put(key, node);
        System.out.println("added node " + value);
    }

    public V get(K key) {
        if (map.containsKey(key) == false) {
            return null;
        }
        CacheItem node = (CacheItem) map.get(key);
        return (V) node.getValue();
    }

    public void delete(K key) {
        deleteNode(map.get(key));
    }

    public int size() {
        return size;
    }

    private void deleteNode(CacheItem node) {
        if (node == null) {
            return;
        }
        if (last == node) {
            last = node.getPrev();
        }
        if (first == node) {
            first = node.getNext();
        }
        map.remove(node.getKey());
        node = null; // Optional, collected by GC
        size--;
    }

    private void addNodeToLast(CacheItem node) {
        if (last != null) {
            last.setNext(node);
            node.setPrev(last);
        }

        last = node;
        if (first == null) {
            first = node;
        }
        size++;
    }

}
