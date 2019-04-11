package com.xpj.classloader.noactiveload.example2;

public class Main {
    public static void main(String[] args) {
        int max = GlobalConstants.MAX; //不会导致GlobalConstants的初始化
        System.out.println("--------------------------------------");
        //会导致GlobalConstants的初始化， 因为在类的加载、连接阶段无法对Random进行计算，需要进行初始化才能对其赋予准确的值
        int random = GlobalConstants.RANDOM;
    }
}
