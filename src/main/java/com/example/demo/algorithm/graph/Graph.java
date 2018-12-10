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

    private final int MAX_VERTS = 20;

    /**
     * 储存顶点的数组
     */
    private Vertex vertexArray[];

    /**
     * 储存是否有边界的矩阵数组，0表示没有边界，1表示有边界
     */
    private int adjMat[][];

    /**
     * 顶点个数
     */
    private int nVerts;

    /**
     * 深度搜索时用来临时储存的栈
     */
    private StackX stackX;

    /**
     * 广度搜索时用来临时储存的队列
     */
    private QueueX queueX;

    public Graph() {
        vertexArray = new Vertex[MAX_VERTS];
        adjMat = new int[MAX_VERTS][MAX_VERTS];
        nVerts = 0;
        for (int i = 0; i < MAX_VERTS; i++) {
            for (int j = 0; j < MAX_VERTS; j++) {
                adjMat[i][j] = 0;
            }
        }
        stackX = new StackX();
        queueX = new QueueX();
    }

    public void addVertex(char lab) {
        vertexArray[nVerts++] = new Vertex(lab);
    }

    public void addEdge(int start, int end) {
        adjMat[start][end] = 1;
        adjMat[start][end] = 1;
    }

    public void displayVertex(int v) {
        System.out.print(vertexArray[v].label);
    }

    /**
     * 深度优先搜索算法：做四件事
     * <p>
     * 1 用peek()方法检查栈顶的顶点
     * 2 试图找到这个顶点还未访问的邻节点
     * 3 如果没有找到，出栈
     * 4 如果找到这样的顶点，访问这个顶点，并把它放入栈
     * 深度优先算法类似于从树的根逐个沿不同路径访问到不同的叶节点
     */
    public void depthFirstSearch() {
        //begin at vertex 0
        vertexArray[0].wasVisited = true;
        displayVertex(0);
        stackX.push(0);
        while (!stackX.isEmpty()) {
            //get an unvisited vertex adjacent to stack top
            int v = getAdjUnvisitedVertex(stackX.peek());
            if (v == -1) {
                stackX.pop();
            } else {
                vertexArray[v].wasVisited = true;
                displayVertex(v);
                stackX.push(v);
            }
        }
        for (int i = 0; i < nVerts; i++) {
            vertexArray[i].wasVisited = false;
        }

    }

    private int getAdjUnvisitedVertex(int v) {
        for (int i = 0; i < nVerts; i++) {
            //v和i之间有边，且i没被访问过
            if (adjMat[v][i] == 1 && !vertexArray[i].wasVisited) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 广度优先搜索算法：做四件事
     * <p>
     * 1. 用remove()方法检查栈顶的顶点
     * 2. 试图找到这个顶点还未访问的邻节点
     * 3. 如果没有找到，该顶点出列
     * 4. 如果找到这样的顶点，访问这个顶点，并把它放入队列中
     * 深度优先算法中，好像表现的要尽快远离起始点，在广度优先算法中，要尽可能靠近起始点。
     * 它首先访问其实顶点的所有邻节点，然后再访问较远的区域。这种搜索不能用栈，而要用队列来实现。
     * 广度优先算法类似于从树的跟逐层往下访问直到底层
     */
    public void breadthFirstSearch() {
        vertexArray[0].wasVisited = true;
        displayVertex(0);
        queueX.insert(0);
        int v2;
        while (!queueX.isEmpty()) {
            int v1 = queueX.remove();
            while ((v2 = getAdjUnvisitedVertex(v1)) != -1) {
                vertexArray[v2].wasVisited = true;
                displayVertex(v2);
                queueX.insert(v2);
            }
        }
        for (int i = 0; i < nVerts; i++) {
            vertexArray[i].wasVisited = false;
        }
    }

    /**
     * 最小生成树，就是用最少的边连接所有的顶点。对于给定的一组顶点，可能又很多种最小生成树，但是最小生成树边E的数量总是比顶点V的数量小1，
     * 即E=V-1。寻找最小生成树不需要关心边的长度，并不需要找到一条最短路径，而是要找最少数量的边
     * <p>
     * 创建最小生成树的算法与搜索算法几乎是相同的，它同样可以基于广度优先搜索和深度优先搜索，这里使用深度优先搜索。在执行深度优先搜索的过程中，
     * 如果记录走过的边，就可以创建一棵最小生成树，可能会感到有点奇怪
     */
    public void minSpanningTree() {
        vertexArray[0].wasVisited = true;
        stackX.push(0);
        while (!stackX.isEmpty()) {
            int currentVertex = stackX.peek();
            int v = getAdjUnvisitedVertex(currentVertex);
            if (v == -1) {
                stackX.pop();
            } else {
                vertexArray[v].wasVisited = true;
                stackX.push(v);
                displayVertex(currentVertex);
                displayVertex(v);
                System.out.print(" ");
            }
        }
        for (int i = 0; i < nVerts; i++) {
            vertexArray[i].wasVisited = false;
        }
    }

}
