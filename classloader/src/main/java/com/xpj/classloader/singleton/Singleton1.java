package com.xpj.classloader.singleton;

public class Singleton1 {
    private static int x = 0;
    private static int y;
    private static Singleton1 singleton = new Singleton1();

    private Singleton1(){
        x++;
        y++;
    }

    public static Singleton1 getInstance(){
        return singleton;
    }

    public static void main(String[] args) {

        Singleton1 instance = Singleton1.getInstance();
        System.out.println(instance.x);
        System.out.println(instance.y);
    }
}
