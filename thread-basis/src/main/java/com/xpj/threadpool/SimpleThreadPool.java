package com.xpj.threadpool;

import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Description:
 *
 * @Author: xupeiji
 * @Date: Created in 2019/4/10 10:21
 */
public class SimpleThreadPool {
    private final int size;
    private final static int DEFAULT_SIZE = 10;

    private volatile int seq = 0;

    private static final String THREAD_PREFIX = "SIMPLE_THREAD_POOL_";

    private static final ThreadGroup GROUP = new ThreadGroup("POOL_GROUP");

    private static final LinkedList<Runnable> TASK_QUEUE = new LinkedList<>();

//    private static final List<WorkerThread> THREAD_QUEUE = new ArrayList<>();

    public SimpleThreadPool() {
        this(DEFAULT_SIZE);
    }

    public SimpleThreadPool(int size) {
        this.size = size;
        init();
    }

    private void init(){
        for (int i = 0; i < size; i++) {
            createWorkerTask();
        }
    }

    private void createWorkerTask(){
        String name = THREAD_PREFIX + (++seq);
        WorkerThread task = new WorkerThread(GROUP, name);
        task.start();
        Optional.of(name + " started").ifPresent(System.out::println);
//        THREAD_QUEUE.add(task);
    }

    public void submit(Runnable runnable){
        synchronized (TASK_QUEUE) {
            TASK_QUEUE.addLast(runnable);
            TASK_QUEUE.notifyAll();
        }
    }

    private enum TaskState{
        FREE, RUNNING, BLOCKED, DEAD
    }

    private static class WorkerThread extends Thread{
        private volatile TaskState taskState = TaskState.FREE;

        public WorkerThread(ThreadGroup threadGroup, String name){
            super();
        }

        public TaskState getTaskState() {
            return this.taskState;
        }

        @Override
        public void run(){
            OUTER:
            while(this.taskState != TaskState.DEAD){
                Runnable task;
                synchronized (TASK_QUEUE){
                    while(TASK_QUEUE.isEmpty()){
                        try {
                            this.taskState = TaskState.BLOCKED;
                            TASK_QUEUE.wait();
                        } catch (InterruptedException e) {
                            break OUTER;
                        }
                    }
                     task = TASK_QUEUE.removeFirst();
                }
                if (task != null){
                    this.taskState = TaskState.RUNNING;
                    task.run();
                    this.taskState = TaskState.FREE;
                }
            }
        }

        public void close(){
            this.taskState = TaskState.DEAD;
        }
    }

    public static void main(String[] args) {
        SimpleThreadPool threadPool = new SimpleThreadPool();
        IntStream.range(0, 40).forEach(i ->
            threadPool.submit(() -> {
                Optional.of("the runnable " + i + " is serviced by " + Thread.currentThread().getName() + " start")
                        .ifPresent(System.out::println);
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Optional.of("the runnable " + i + " is serviced by " + Thread.currentThread().getName() + " end")
                        .ifPresent(System.out::println);
            })
        );
    }
}
