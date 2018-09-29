package com.example.demo.algorithm;

import java.util.TreeMap;

/**
 * Trie树（前缀树、字典树）
 *
 * @author dinghuang123@gmail.com
 * @since 2018/9/29
 */


public class Trie {

    private Node root;
    private int size;

    public Trie() {
        root = new Node();
        size = 0;
    }

    public int getSize() {
        return size;
    }

    /**
     * 添加一个单词
     *
     * @param word
     */
    public void add(String word) {
        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (cur.next.get(c) == null) {
                cur.next.put(c, new Node());
            }
            cur = cur.next.get(c);
        }
        if (!cur.isWord) {
            cur.isWord = true;
            size++;
        }
    }

    /**
     * 递归添加一个单词
     *
     * @param word
     */
    public void addRecursion(String word) {
        root = addRecursion(root, word);
    }

    /**
     * 在node节点添加一个单词
     *
     * @param node
     * @param word
     */
    private Node addRecursion(Node node, String word) {
        if (node == null) {
            return null;
        }
        if (word.length() > 0) {
            if (node.next.get(word.charAt(0)) == null) {
                node.next.put(word.charAt(0), new Node());
            }
            node.next.put(word.charAt(0), addRecursion(node.next.get(word.charAt(0)), word.substring(1)));
            if (word.length() == 1 && !node.next.get(word.charAt(0)).isWord) {
                node.next.get(word.charAt(0)).isWord = true;
                size++;
            }
        }
        return node;
    }

    /**
     * 是否包含某个单词
     *
     * @param word
     * @return boolean
     */
    public boolean contains(String word) {
        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (cur.next.get(c) == null) {
                return false;
            }
            cur = cur.next.get(c);
        }
        return cur.isWord;
    }

    /**
     * 递归查询是否包含某个单词
     *
     * @param word
     * @return boolean
     */
    public boolean containsRecurison(String word) {
        return containsRecurison(root, word);
    }

    /**
     * 递归查询某个节点中是否包含某个单词
     *
     * @param node
     * @param word
     * @return boolean
     */
    private boolean containsRecurison(Node node, String word) {
        if (node == null) {
            return false;
        }
        if (word.length() > 0) {
            if (node.next.get(word.charAt(0)) == null) {
                return false;
            } else if (word.length() == 1 && node.next.get(word.charAt(0)).isWord) {
                return true;
            }
            return containsRecurison(node.next.get(word.charAt(0)), word.substring(1));
        }
        return false;
    }

    /**
     * 是否包含此前缀的单词
     *
     * @param prefix
     * @return boolean
     */
    private boolean isPrefix(String prefix) {
        Node cur = root;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            if (cur.next.get(c) == null) {
                return false;
            }
            cur = cur.next.get(c);
        }
        return true;
    }


    /**
     * 递归查询是否包含此前缀的单词
     *
     * @param word
     * @return boolean
     */
    public boolean isPrefixRecurison(String word) {
        return isPrefixRecurison(root, word);
    }

    /**
     * 递归查询是否包含此前缀的单词
     *
     * @param node
     * @param word
     * @return boolean
     */
    private boolean isPrefixRecurison(Node node, String word) {
        if (node == null) {
            return false;
        }
        if (word.length() > 0) {
            if (node.next.get(word.charAt(0)) == null) {
                return false;
            } else if (word.length() == 1) {
                return true;
            }
            return isPrefixRecurison(node.next.get(word.charAt(0)), word.substring(1));
        }
        return false;
    }


    /**
     * 节点类
     */
    private class Node {
        public boolean isWord;
        public TreeMap<Character, Node> next;

        public Node(boolean isWord) {
            this.isWord = isWord;
            next = new TreeMap<>();
        }

        public Node() {
            this(false);
        }
    }
}