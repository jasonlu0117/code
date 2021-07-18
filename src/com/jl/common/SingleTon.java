package com.jl.common;

public class SingleTon {

    private static SingleTon instance;

    private SingleTon() {
    }

    public synchronized static SingleTon getInstance() {
        if (instance == null) {
            instance = new SingleTon();
        }
        return instance;
    }

}

