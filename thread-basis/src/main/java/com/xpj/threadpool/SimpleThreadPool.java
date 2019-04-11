package com.xpj.threadpool;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Description:
 *
 * @Author: xupeiji
 * @Date: Created in 2019/4/10 10:21
 */
public class SimpleThreadPool extends Thread{

    /**线程池大小*/
    private int size;

    /**自增 用于线程名称命名*/
    private volatile int seq = 0;

    /**任务队列最大可接受队列个数*/
    private final int taskQueueSize;

    /**是否已经关闭线程池*/
    private volatile boolean isShutdown = false;

    /**拒绝策略*/
    private DiscardPolicy discardPolicy;

    /**默认任务队列大小*/
    private static final int DEFAULT_TASK_QUEUE_SIZE = 2000;

    /**线程名称前缀*/
    private static final String THREAD_PREFIX = "SIMPLE_THREAD_POOL_";

    /**默认线程组*/
    private static final ThreadGroup GROUP = new ThreadGroup("POOL_GROUP");

    /**任务队列*/
    private static final LinkedList<Runnable> TASK_QUEUE = new LinkedList<>();

    /**线程池线程队列*/
    private static final List<WorkerThread> THREAD_QUEUE = new ArrayList<>();

    /**最小线程数*/
    private int min;
    /**最大线程数*/
    private int max;
    /**活跃线程数*/
    private int active;

    /**默认拒绝策略*/
    private static final DiscardPolicy DEFAULT_DISCARD_POLICY = () -> {
        throw new DiscardException("discard the task");
    };

    public SimpleThreadPool() {
        this(4, 8, 12, DEFAULT_TASK_QUEUE_SIZE, DEFAULT_DISCARD_POLICY);
    }

    public SimpleThreadPool(int min, int active, int max, int taskQueueSize) {
        this(min, active, max, taskQueueSize, DEFAULT_DISCARD_POLICY);
    }

    public SimpleThreadPool(int min, int active, int max, int taskQueueSize, DiscardPolicy discardPolicy) {
        this.min = min;
        this.active = active;
        this.max = max;
        this.taskQueueSize = taskQueueSize;
        this.discardPolicy = discardPolicy;
        init();
    }

  /*  private void init(){
        for (int i = 0; i < size; i++) {
            createWorkerTask();
        }
    }*/

    /**初始化时默认初始化一个最小线程数的线程池*/
    private void init(){
        for (int i = 0; i < min; i++) {
            createWorkerTask();
        }
        this.size = min;
        this.start();
    }

    
    @Override
    public void run() {
        while(!isShutdown){
            System.out.printf("min:%d,active:%d,max:%d,current:%d,taskQueueSize:%d\n",
                    this.min, this.active, this.max, this.size, TASK_QUEUE.size());
            try {
                TimeUnit.SECONDS.sleep(1);
                if (TASK_QUEUE.size() > active && size < active){
                    for (int i = size; i < active; i++) {
                        createWorkerTask();
                    }
                    size = active;
                    System.out.println("threadpool resize to active");
                }else if (TASK_QUEUE.size() > max && size < max){
                    for (int i = size; i < active; i++) {
                        createWorkerTask();
                    }
                    size = max;
                    System.out.println("threadpool resize to max");
                }

                if (TASK_QUEUE.isEmpty() && size > active){
                    int releaseSize = size - active;
                    for(Iterator<WorkerThread> it = THREAD_QUEUE.iterator(); it.hasNext();){
                        if (releaseSize <= 0){
                            break;
                        }

                        WorkerThread workerThread = it.next();
                        workerThread.interrupt();
                        workerThread.close();
                        it.remove();
                        releaseSize--;
                    }
                    size = active;
                    System.out.println("threadpool reduce to active");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void createWorkerTask(){
        String name = THREAD_PREFIX + (++seq);
        WorkerThread task = new WorkerThread(GROUP, name);
        task.start();
        THREAD_QUEUE.add(task);
    }

    public void submit(Runnable runnable){
        if (isShutdown){
            throw new IllegalStateException("threadpool already shutdown ,cann't submit task!");
        }
        synchronized (TASK_QUEUE) {
            if (TASK_QUEUE.size() >= taskQueueSize){
                discardPolicy.discard();
            }
            TASK_QUEUE.addLast(runnable);
            TASK_QUEUE.notifyAll();
        }
    }

    public void shutdown() throws InterruptedException {
        isShutdown = true;
        while(!TASK_QUEUE.isEmpty()){
            TimeUnit.MILLISECONDS.sleep(50);
        }
        int threadQueueSize = THREAD_QUEUE.size();
        while(threadQueueSize > 0){
            for (WorkerThread workerThread : THREAD_QUEUE) {
                if (workerThread.taskState == TaskState.BLOCKED){
                    workerThread.interrupt();
                    workerThread.close();
                    threadQueueSize--;
                }else{
                    TimeUnit.MILLISECONDS.sleep(50);
                }
            }
        }
    }

    public static class DiscardException extends RuntimeException{
        public DiscardException(String message) {
            super(message);
        }
    }

    public interface DiscardPolicy{
        void discard() throws DiscardException;
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

    public static void main(String[] args) throws InterruptedException {
        SimpleThreadPool threadPool = new SimpleThreadPool();
        IntStream.range(0, 40).forEach(i ->
            threadPool.submit(() -> {
                Optional.of("the runnable " + i + " is serviced by " + Thread.currentThread().getName() + " start")
                        .ifPresent(System.out::println);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Optional.of("the runnable " + i + " is serviced by " + Thread.currentThread().getName() + " end")
                        .ifPresent(System.out::println);
            })
        );
//        threadPool.shutdown();
        System.out.println("--------------main-------------------");
        TimeUnit.MILLISECONDS.sleep(50);
        threadPool.submit(() -> {

        });
    }
}
