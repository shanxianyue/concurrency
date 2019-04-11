package com.xpj.classloader.activeload.example3;

public class StaticMethod {
    public static void method(){}

    static {
        System.out.println("loaded");
    }
}
