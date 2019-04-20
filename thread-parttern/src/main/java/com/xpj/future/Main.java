package com.xpj.future;

public class Main {
    public static void main(String[] args) {
        Data a = new Host().request(100, 'A');
        String content = a.getContent();
        System.out.println(content);
    }
}
