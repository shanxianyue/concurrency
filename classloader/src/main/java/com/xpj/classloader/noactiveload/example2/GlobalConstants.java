package com.xpj.classloader.noactiveload.example2;

import java.util.Random;

public class GlobalConstants {
    static {
        System.out.println("loaded");
    }

    public static final int MAX = 5;
    public static final int RANDOM = new Random(5).nextInt();
}
