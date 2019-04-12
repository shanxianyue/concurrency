package com.xpj.singleton;


/**双重校验锁 缺点：由于指令重排和happen-before原则，
 * 一个线程获取到锁后，有可能先实例化出了instance，然后才初始化other实例，
 * 而另外一个线程可能在已经实例出instance，还未实例好other时调用getInstance，
 * 然后判断instance不为空而直接返回，这样就有可能在使用instance实例时，出现异常。
 *
 * 注：图解分析请参看resources/图解Double-Check可能引起空指针异常分析.png
 *
 * 解决方法：可以使用volatile修饰instance变量来禁止指令重排来解决。
 */
public class Singleton3 {
    private static Singleton3 instance = new Singleton3();
    private Object other;
    private Singleton3(){
        //初始化一些实例
        other = new Object();
    }
    public static Singleton3 getInstance(){
        if (instance == null){
            synchronized (Singleton3.class){
                if (instance == null) {
                    instance = new Singleton3();
                }
            }
        }
        return instance;
    }
}
