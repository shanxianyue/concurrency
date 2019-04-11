package com.xpj.classloader.activeload.example2;

public class Simple {
    static {
        System.out.println("loaded");
    }

    public static int i = 2;
}
