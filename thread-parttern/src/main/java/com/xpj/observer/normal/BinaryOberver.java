package com.xpj.observer.normal;

public class BinaryOberver extends Observer {

    public BinaryOberver(Subject subject){
        super(subject);
    }

    @Override
    void update() {
        System.out.println("Binary String:" + Integer.toBinaryString(subject.getState()));
    }
}
