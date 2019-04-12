package com.xpj.singleton;


/**
 * 静态内部类。 利用static的特性实现无锁的懒加载，推荐使用
 */
public class Singleton4 {

    public static int i = 1;

    static {
        System.out.println("Singleton4 loaded");
    }

    private Singleton4(){

    }

    private static class InstanceHolder{
        static {
            System.out.println("InstanceHolder loaded");
        }
        private static final Singleton4 instance = new Singleton4();
    }

    public static Singleton4 getInstance(){
        return InstanceHolder.instance;
    }

}
