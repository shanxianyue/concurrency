package com.xpj.singleton;

/**
 * 枚举方式 推荐 优点：懒加载 无锁
 */
public class Singleton5 {

    private Singleton5(){
        //初始化实例操作
    }


    private enum InstanceEnum{
        INSTANCE;

        static {
            System.out.println("InstanceEnum loaded");
        }

        private Singleton5 instance;
        private InstanceEnum(){
            instance = new Singleton5();
        }

        public Singleton5 getInstance(){
            return instance;
        }
    }

    public static Singleton5 getInstance(){
        return InstanceEnum.INSTANCE.getInstance();
    }

}

/**
 * 也可以这样实现
 */
enum Singleton6{
    INSTANCE;
    Singleton6(){
        System.out.println("初始化操作");
    }
}
