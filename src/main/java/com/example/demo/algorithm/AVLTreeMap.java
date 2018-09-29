package com.example.demo.algorithm;

/**
 * 利用AVL平衡二叉树实现映射
 *
 * @author dinghuang123@gmail.com
 * @since 2018/9/29
 */
public class AVLTreeMap<K extends Comparable<K>, V> implements Map<K, V> {

    AVLTree<K, V> avlTree;

    public AVLTreeMap() {
        avlTree = new AVLTree();
    }

    @Override
    public void add(K key, V value) {
        avlTree.add(key, value);
    }

    @Override
    public V remove(K key) {
        return avlTree.remove(key);
    }

    @Override
    public boolean contains(K key) {
        return avlTree.contains(key);
    }

    @Override
    public V get(K key) {
        return avlTree.get(key);
    }

    @Override
    public void set(K key, V value) {
        avlTree.set(key, value);
    }

    @Override
    public int getSize() {
        return avlTree.getSize();
    }

    @Override
    public boolean isEmpty() {
        return avlTree.isEmpty();
    }
}
