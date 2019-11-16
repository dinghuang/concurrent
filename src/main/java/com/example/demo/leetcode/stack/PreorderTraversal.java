package com.example.demo.leetcode.stack;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * 给定一个二叉树，返回它的 前序 遍历。
 * <p>
 *  示例:
 * <p>
 * 输入: [1,null,2,3]
 *          1
 *          \
 *          2
 *         /
 *        3
 * <p>
 * 输出: [1,2,3]
 * 进阶: 递归算法很简单，你可以通过迭代算法完成吗？
 *
 * @author dinghuang123@gmail.com
 * @since 2019/11/11
 */
public class PreorderTraversal {

    public static List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new LinkedList<>();
        if (root == null) {
            return result;
        }
        Deque<TreeNode> deques = new ArrayDeque<>();
        deques.push(root);
        while (!deques.isEmpty()) {
            TreeNode treeNode = deques.pop();
            result.add(treeNode.val);
            if (treeNode.right != null) {
                deques.push(treeNode.right);
            }
            if (treeNode.left != null) {
                deques.push(treeNode.left);
            }
        }
        return result;

    }

    public static void main(String[] args) {
        TreeNode data = new TreeNode(1);
        data.right = new TreeNode(2);
        data.right.left = new TreeNode(3);
        System.out.println(preorderTraversal(data));

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
