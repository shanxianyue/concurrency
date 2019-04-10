package com.xpj.lock;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * Description:
 *
 * @Author: xupeiji
 * @Date: Created in 2019/4/9 10:32
 */
public class BooleanLockTest {
    public static void main(String[] args) throws InterruptedException {
        final BooleanLock lock = new BooleanLock();
        Stream.of(1, 2, 3, 4).forEach(index -> new Thread(() -> {
            try {
                lock.lock(index * 1000);
                Optional.of(Thread.currentThread().getName() + " have the monitor!").ifPresent(System.out::println);
                work();
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " interupted");
            } catch (CustomsizeLock.TimeOutException e) {
                System.err.println(Thread.currentThread().getName() + " timeout");
            } finally {
                lock.unlock();
            }
        }, "T" + index).start());
        TimeUnit.SECONDS.sleep(1);
        lock.unlock();
    }

    public static void work() throws InterruptedException {
        Optional.of(Thread.currentThread().getName() + " is working").ifPresent(System.out::println);
        TimeUnit.SECONDS.sleep(10);
        Optional.of(Thread.currentThread().getName() + " is finished").ifPresent(System.out::println);
    }
}
