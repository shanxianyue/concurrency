package com.xpj.singleton;

/**饿汉式 缺点：不能懒加载*/
public class Singleton1 {
    private static final Singleton1 instance = new Singleton1();
    private Singleton1(){}
    public static Singleton1 getInstance() {
        return instance;
    }
}
