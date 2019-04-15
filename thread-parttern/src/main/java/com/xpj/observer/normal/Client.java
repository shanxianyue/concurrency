package com.xpj.observer.normal;

public class Client {
    public static void main(String[] args) {
        Subject subject = new Subject();
        new BinaryOberver(subject);
        subject.setState(2);
        subject.setState(3);
    }
}
