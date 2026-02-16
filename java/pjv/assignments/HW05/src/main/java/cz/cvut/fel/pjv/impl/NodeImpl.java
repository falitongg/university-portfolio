package cz.cvut.fel.pjv.impl;

import cz.cvut.fel.pjv.Node;

public class NodeImpl implements Node {

    // TODO: implement this class
    private int value;
    private Node left;
    private Node right;

    public NodeImpl(int value) {
        this.value = value;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    @Override
    public Node getLeft() {
        return left;
    }

    @Override
    public Node getRight() {
        return right;
    }

    @Override
    public int getValue() {
        return value;
    }
}
