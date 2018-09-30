package com.example.demo.algorithm;

/**
 * 动态数组
 *
 * @author dinghuang123@gmail.com
 * @since 2018/9/29
 */
public class Array<E> {

    private E[] data;
    private int size;

    @SuppressWarnings("unchecked")
    Array(int capacity) {
        data = (E[]) new Object[capacity];
        size = 0;
    }

    Array() {
        this(10);
    }

    public int getSize() {
        return size;
    }

    int getCapacity() {
        return data.length;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 向数组头部插入一个元素
     *
     * @param e e
     */
    public void addFirst(E e) {
        add(0, e);
    }

    /**
     * 向数组尾部插入一个元素
     *
     * @param e e
     */
    public void addLast(E e) {
        add(size, e);
    }

    /**
     * 向数组指定位置插入一个元素
     *
     * @param index index
     * @param e     e
     */
    public void add(int index, E e) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Add failed. Require index >= 0 and index <= size");
        }
        if (size == data.length) {
            //数组动态扩容两倍
            resize(2 * data.length);
        }
        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = e;
        size++;
    }

    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        E[] newData = (E[]) new Object[newCapacity];
        System.arraycopy(data, 0, newData, 0, size);
        data = newData;
    }

    /**
     * 获取指定位置的元素
     *
     * @param index index
     * @return E
     */
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Get failed. index is Illegal.");
        }
        return data[index];
    }

    /**
     * 获取第一个元素
     *
     * @return E
     */
    E getFirst() {
        return data[0];
    }

    /**
     * 获取最后一个元素
     *
     * @return E
     */
    E getLast() {
        return get(size - 1);
    }

    /**
     * 修改指定位置的元素
     *
     * @param index index
     * @param e     e
     */
    public void set(int index, E e) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Set failed. index is Illegal.");
        }
        data[index] = e;
    }

    /**
     * 查看数组中是否包含某个元素
     *
     * @param e e
     * @return boolean
     */
    public boolean contains(E e) {

        return indexOf(e) > 0;
    }

    /**
     * 查找元素在数组中的位置
     *
     * @param e e
     * @return int
     */
    private int indexOf(E e) {
        if (e == null) {
            for (int i = 0; i < size; i++) {
                if (data[i] == null) {
                    return i;
                }
            }

        } else {
            for (int i = 0; i < size; i++) {
                if (data[i].equals(e)) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 移除数组中的一个元素
     *
     * @param index index
     * @return int
     */
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Remove failed. index is Illegal.");
        }

        E result = data[index];
        System.arraycopy(data, index + 1, data, index + 1 - 1, size - index + 1);
        size--;
        //修改对象引用，垃圾回收机制回收
        data[size] = null;

        //动态缩小数组一半容量
        if (size == data.length >> 2 && data.length >> 1 != 0) {
            resize(data.length >> 1);
        }
        return result;
    }

    /**
     * 移除数组中的第一个元素
     *
     * @return E
     */
    E removeFirst() {
        return remove(0);
    }

    /**
     * 移除数组中的最后一个元素
     *
     * @return int
     */
    E removeLast() {
        return remove(size - 1);
    }

    /**
     * 移除数组中的某个元素
     *
     * @param e e
     */
    public void removeElement(E e) {
        int index = indexOf(e);
        if (index != -1) {
            remove(index);
        }
    }

    /**
     * 将索引为i和j的两个元素互相交换
     *
     * @param i i
     * @param j jj
     */
    void swap(int i, int j) {
        if (i < 0 || i >= size || j < 0 || j >= size) {
            throw new IllegalArgumentException("Swap failed. index is Illegal.");
        }
        E temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    /**
     * 自定义toString方法
     *
     * @return String
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(String.format("Array: size = %d , capacity = %d%n", size, data.length));
        result.append("[");
        for (int i = 0; i < size; i++) {
            result.append(data[i]);
            if (i != size - 1) {
                result.append(", ");
            }
        }
        result.append("]");
        return result.toString();
    }

}
