package com.jl.common;

public class NNode {
    public int val;
    public NNode left;
    public NNode right;
    public NNode next;

    public NNode() {
    }

    public NNode(int _val) {
        val = _val;
    }

    public NNode(int _val, NNode _left, NNode _right, NNode _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }
}
