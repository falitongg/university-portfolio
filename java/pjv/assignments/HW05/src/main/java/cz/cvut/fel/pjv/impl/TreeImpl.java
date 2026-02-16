package cz.cvut.fel.pjv.impl;

import cz.cvut.fel.pjv.Node;
import cz.cvut.fel.pjv.Tree;

public class TreeImpl implements Tree {

    // TODO: implement this class
    private Node root;

    public TreeImpl() {
    }

    @Override
    public void setTree(int[] values) {
        if (values == null || values.length == 0) {
            root = null;
            return;
        }
        root = buildTree(values, 0, values.length);
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        buildString(root, 0, sb);
        return sb.toString();
    }

    public Node buildTree(int[] values, int start, int end) {
        if (start >= end) {
            return null;
        }
        int length = end - start;
        int mid = (start + end) / 2;
        NodeImpl node = new NodeImpl(values[mid]);
        node.setLeft(buildTree(values, start, mid));
        node.setRight(buildTree(values, mid + 1, end));
        return node;
    }
    private void buildString(Node node, int level, StringBuilder sb) {
        if (node == null) {
            return;
        }

        for (int i = 0; i < level; i++) {
            sb.append(' ');
        }

        sb.append("- ")
                .append(node.getValue())
                .append("\n");

        buildString(node.getLeft(), level + 1, sb);
        buildString(node.getRight(), level + 1, sb);
    }



}
