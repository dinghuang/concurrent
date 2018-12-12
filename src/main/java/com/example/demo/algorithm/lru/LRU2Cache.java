package com.example.demo.algorithm.lru;

import java.util.HashMap;
import java.util.Map;

/**
 * Least Recently Used 2，有人叫我最近最少使用 twice，我更喜欢这个叫法。我会把被两次访问过的对象放入缓存池，当缓存池满了之后，
 * 我会把有两次最少使用的缓存对象踢走。因为需要跟踪对象2次，访问负载就会随着缓存池的增加而增加。如果把我用在大容量的缓存池中，
 * 就会有问题。另外，我还需要跟踪那么不在缓存的对象，因为他们还没有被第二次读取。我比LRU好，而且是 adoptive to access 模式 。
 *
 * @author dinghuang123@gmail.com
 * @since 2018/12/12
 */
public class LRU2Cache<K, V extends Comparable<V>> {

    private MaxHeap<K, V> maxHeap;
    private Map<K, Node<K, V>> map;

    public LRU2Cache(int cacheSize) {
        maxHeap = new MaxHeap<>(cacheSize);
        map = new HashMap<>((int) ((float) cacheSize / 0.75F + 1.0F));
    }

    public void put(K key, V value) {
        if (key != null && value != null) {
            Node<K, V> previous;
            if ((previous = map.get(key)) != null) {
                maxHeap.remove(previous.getIndex());
            }
            if (maxHeap.isFull()) {
                map.remove(maxHeap.getMax().getKey());
            }
            previous = new Node<>(key, value);
            map.put(key, previous);
            maxHeap.add(previous);
        }
    }

    public V get(K key) {
        V value = null;
        if (key != null) {
            Node<K, V> node = map.get(key);
            if (node != null) {
                value = node.getValue();
                maxHeap.reVisited(node.getIndex());
            }
        }
        return value;
    }

    class Node<A, B extends Comparable<B>> implements Comparable<Node<A, B>> {
        private A key;
        private B value;
        private int index;
        private long lastTime;
        private long lastSecondTime;
        private static final long INIT = -1;

        Node(A key, B value) {
            this.key = key;
            this.value = value;
            lastTime = INIT;
            lastSecondTime = INIT;
        }

        @Override
        public int compareTo(Node<A, B> node) {
            return (int) (lastSecondTime - node.getLastSecondTime());
        }

        A getKey() {
            return key;
        }

        B getValue() {
            return value;
        }

        int getIndex() {
            return index;
        }

        void setIndex(int index) {
            this.index = index;
        }

        long getLastTime() {
            return lastTime;
        }

        void setLastTime(long lastTime) {
            this.lastTime = lastTime;
        }

        long getLastSecondTime() {
            return lastSecondTime;
        }

        void setLastSecondTime(long lastSecondTime) {
            this.lastSecondTime = lastSecondTime;
        }
    }

    class MaxHeap<C, D extends Comparable<D>> {

        private Node<C, D>[] heap;
        private int currentSize;
        private long count;

        @SuppressWarnings("unchecked")
        MaxHeap(int size) {
            count = 0;
            currentSize = 1;
            heap = new Node[size + 1];
        }

        boolean isFull() {
            return currentSize >= heap.length;
        }

        Node<C, D> add(Node<C, D> value) {
            Node<C, D> previous = value;
            if (currentSize >= heap.length) {
                previous = removeMax();
            }
            if (value.getLastSecondTime() != Node.INIT) {
                value.setLastSecondTime(value.getLastTime());
            } else {
                value.setLastSecondTime(count);
            }
            value.setLastTime(count++);
            value.setIndex(currentSize);
            heap[currentSize++] = value;
            siftUp(currentSize - 1);
            return previous;
        }

        Node<C, D> getMax() {
            return heap[0];
        }

        Node<C, D> removeMax() {
            return remove(0);
        }

        Node<C, D> reVisited(int index) {
            Node<C, D> node = heap[index];
            remove(node.getIndex());
            add(node);
            return node;
        }

        Node<C, D> remove(int index) {
            Node<C, D> previous = heap[index];
            heap[index] = heap[--currentSize];
            siftDown(index);
            return previous;
        }

        private void siftDown(int index) {
            int left = 2 * index;
            int right = 2 * index + 1;
            int largest;
            if (left < currentSize && heap[left].compareTo(heap[index]) > 0) {
                largest = left;
            } else {
                largest = index;
            }
            if (right < currentSize && heap[right].compareTo(heap[largest]) > 0) {
                largest = right;
            }
            if (largest != index) {
                Node<C, D> temp = heap[index];
                heap[index] = heap[largest];
                heap[largest] = temp;
                heap[index].setIndex(largest);
                heap[largest].setIndex(index);
                siftDown(largest);
            }
        }

        private void siftUp(int index) {
            while (index > 1 && heap[index].compareTo(heap[index / 2]) > 0) {
                Node<C, D> temp = heap[index];
                heap[index] = heap[index / 2];
                heap[index / 2] = temp;
                heap[index].setIndex(index / 2);
                heap[index / 2].setIndex(index);
                index = index / 2;
            }
        }
    }
}
