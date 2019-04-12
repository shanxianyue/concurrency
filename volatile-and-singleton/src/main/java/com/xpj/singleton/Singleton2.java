package com.xpj.singleton;

import java.util.concurrent.TimeUnit;

/**懒汉式 缺点：必须添加synchronized才能保证线程安全*/
public class Singleton2 {
    private static Singleton2 instance;
    private Singleton2(){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private synchronized static Singleton2 getInstance(){
        if (instance == null){
            instance = new Singleton2();
        }
        return Singleton2.instance;
    }
}
