package com.xpj.observer.normal;


public abstract class Observer {

    protected Subject subject;

    public Observer(Subject subject){
        this.subject = subject;
        subject.attach(this);
    }

    abstract void update();
}
