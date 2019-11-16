package com.example.demo.leetcode.stack;

import java.util.*;

/**
 * 给定一个二叉树，返回它的中序 遍历。
 * 示例:
 * <p>
 * 输入: [1,null,2,3]
 * 1
 * \
 * 2
 * /
 * 3
 * <p>
 * 输出: [1,3,2]
 * <p>
 * 进阶: 递归算法很简单，你可以通过迭代算法完成吗？
 *
 * @author dinghuang123@gmail.com
 * @since 2019/11/11
 */
public class InorderTraversal {

    public static List<Integer> inorderTraversal(TreeNode root) {
        //基于栈的中序遍历
        List<Integer> list = new LinkedList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
            if (cur != null) {
                stack.push(cur);
                cur = cur.left;
            } else {
                cur = stack.pop();
                list.add(cur.val);
                cur = cur.right;
            }
        }
        return list;
    }

    public static void main(String[] args) {
        TreeNode data = new TreeNode(1);
        data.right = new TreeNode(2);
        data.right.left = new TreeNode(3);
        System.out.println(inorderTraversal(data));

    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}
