package com.example.demo.algorithm.graph;

/**
 * 在图中实现的基本操作之一就是搜索从一个定到可以到达其他哪些顶点，或者找所有当前顶点可到达的顶点。有两种常用的方法可用来搜索图：
 * 深度优先搜索（DFS）和广度优先搜索（BFS），
 * 它们最终都会到达所有的连通顶点。深度优先搜索通过栈来实现，而广度优先搜索通过队列来实现。
 *
 * @author dinghuang123@gmail.com
 * @since 2018/11/29
 */
public class Graph {

    private final int MAX_VERTS =20;

    /**
     * 储存顶点的数组
     */
    private Vertex vertexArray[];

    /**
     * 储存是否有边界的矩阵数组，0表示没有边界，1表示有边界
     *
     */
    private int adjMat[][];

    /**
     * 顶点个数
     */
    private int nVerts;


}
