package com.example.demo.algorithm.graph;

/**
 * 顶点表示某个真实世界的对象，这个对象必须用数据项来描述。例如顶点代表城市，那么它需要存储城市名字、海拔高度、地理位置等相关信息。
 * 因此通常用一个顶点类的对象来表示一个顶点。
 * 这里我们仅仅在顶点中存储了一个字母来标识顶点，同时还有一个标志位，用来判断该顶点有没有被访问过。
 * @author dinghuang123@gmail.com
 * @since 2018/11/29
 */
public class Vertex {

    public char label;
    public boolean wasVisited;

    public Vertex(char label) {
        label = label;
        wasVisited= false;
    }
}
