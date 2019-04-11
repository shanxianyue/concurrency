package com.xpj.classloader.singleton;

public class Singleton2 {
    private static Singleton2 singleton = new Singleton2();
    private static int y;
    private static int x = 0;

    private Singleton2(){
        x++;
        y++;
    }

    public static Singleton2 getInstance(){
        return singleton;
    }

    public static void main(String[] args) {

        Singleton2 instance = Singleton2.getInstance();
        System.out.println(instance.x);
        System.out.println(instance.y);
    }
}
