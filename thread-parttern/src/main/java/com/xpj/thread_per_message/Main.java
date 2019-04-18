package com.xpj.thread_per_message;

public class Main {
    public static void main(String[] args) {
        System.out.println("main START");
        Host host = new Host();
        host.request(3, 'A');
        host.request(4, 'B');
//        host.request(5, 'C');
        System.out.println("main END");
    }
}
