package com.xpj.thread_specific_storage.pattern;

public class Main {
    public static void main(String[] args) {
        new ClientThread("A").start();
        new ClientThread("B").start();
        new ClientThread("C").start();
    }
}
