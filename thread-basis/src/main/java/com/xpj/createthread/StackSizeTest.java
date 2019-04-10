package com.xpj.createthread;

/**
 * @Author: xupeiji
 * @Description:
 * @Date: Created in 2019/4/3 9:56
 * @Modified:
 */
public class StackSizeTest {

    /**
     * stackSize的详细作用请查看
     * {@link Thread(ThreadGroup group, Runnable target, String name, long stackSize)} {@code stackSize}
     */

    /**stackSize越大，递归的层次越深 */
    public static void main(String[] args) {
        Thread t = new Thread(null, new Runnable() {
            private int counter = 0;

            @Override
            public void run() {
                try {
                    add1(0);
                }catch (Throwable e){
                    System.out.println(counter);
                    throw e;
                }
            }

            private void add1(int index){
                ++counter;
                add1(index + 1);
            }

        }, "thread", 1 << 24);
        t.start();
    }
}
