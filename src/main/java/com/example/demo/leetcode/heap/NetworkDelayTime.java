package com.example.demo.leetcode.heap;

/**
 * 有 n 个网络节点，标记为 1 到 n。
 * <p>
 * 给定一个列表 times，表示信号经过有向边的传递时间。 times[i] = (u, v, w)，其中 u 是源节点，v 是目标节点， w 是一个信号从源节点传递到目标节点的时间。
 * <p>
 * 现在，我们向当前的节点 k 发送了一个信号。需要多久才能使所有节点都收到信号？如果不能使所有节点收到信号，返回 -1。
 * <p>
 * 注意:
 * <p>
 * n 的范围在 [1, 100] 之间。
 * k 的范围在 [1, n] 之间。
 * times 的长度在 [1, 6000] 之间。
 * 所有的边 times[i] = (u, v, w) 都有 1 <= u, v <= n 且 0 <= w <= 100。
 *
 * @author dinghuang123@gmail.com
 * @since 2019/11/22
 */
public class NetworkDelayTime {

    public static int networkDelayTime(int[][] times, int n, int k) {
        //DisjKstra算法-邻接矩阵
        //表示无穷远
        int inf = 1000000000;
        //最大距离
        int max = -1;
        //矩阵
        int[][] graph = new int[n + 1][n + 1];
        //标志
        boolean[] flag = new boolean[n + 1];
        //点K到各点的最短距离
        int[] path = new int[n + 1];
        //初始化
        for (int i = 1; i < graph.length; i++) {
            for (int j = 1; j < graph[i].length; j++) {
                graph[i][j] = inf;
            }
        }
        //读入边
        for (int[] time : times) {
            graph[time[0]][time[1]] = time[2];
        }
        for (int i = 1; i < path.length; i++) {
            path[i] = 1000000000;
        }
        path[k] = 0;

        while (true) {
            int index = -1;
            int min = Integer.MAX_VALUE;
            //找出最小值
            for (int i = 1; i < graph.length; i++) {
                //没有判定距离是否为无限距离，此处不影响
                if (path[i] < min && !flag[i]) {
                    min = path[i];
                    index = i;
                }
            }

            if (index == -1) {
                break;

            }
            flag[index] = true;
            //优化
            for (int i = 1; i < graph[index].length; i++) {
                if (graph[index][i] != inf && !flag[i] && path[index] + graph[index][i] < path[i]) {
                    path[i] = path[index] + graph[index][i];
                }
            }
        }
        //找出最大距离
        for (int i = 1; i < path.length; i++) {
            if (path[i] == inf) {
                return -1;

            } else if (max < path[i]) {
                max = path[i];
            }
        }

        return max;
    }

    public static void main(String[] args) {
        int[][] nums = {{2, 1, 1}, {2, 3, 1}, {3, 4, 1}};
        System.out.println(networkDelayTime(nums, 4, 2));

    }
}
