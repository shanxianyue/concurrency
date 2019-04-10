package com.xpj.createthread;

/**
 * @Author: xupeiji
 * @Description:
 * @Date: Created in 2019/4/3 9:56
 * @Modified:
 */
public class StackSizeTest2 {

    /**
     * stackSize的详细作用请查看
     * {@link Thread(ThreadGroup group, Runnable target, String name, long stackSize)} {@code stackSize}
     */

    /**
     * stackSize越大，可创建的线程的个数越少
     */
    public static void main(String[] args) {

        long counter = 0L;
        try {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                new Thread(null, () -> {
                    while (true){}
                }, "thread-" + i, 1 << 32).start();
            }
        } catch (Throwable e){
            System.out.println(counter);
        }
    }
}
