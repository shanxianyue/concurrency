package com.xpj.observer.thread;

public class ObservableThread<T> extends Thread implements Observable {

    /**相当于一个线程生命周期的监听器*/
    private TaskLifecycle<T> lifecycle;

    /**要执行的任务*/
    private Task<T> task;

    /**线程状态枚举*/
    private Cycle cycle;

    public ObservableThread(Task<T> task){
        this(new TaskLifecycle.EmptyLifecycle<>(), task);
    }

    public ObservableThread(TaskLifecycle<T> taskLifecycle, Task<T> task){
        if (task == null){
            throw new IllegalArgumentException("the task is required!");
        }

        this.lifecycle = taskLifecycle;
        this.task = task;
    }

    @Override
    public void run() {
        this.update(Cycle.STARTED, null, null);
        try {
            this.update(Cycle.RUNNING, null, null);
            T result = this.task.call();
            this.update(Cycle.DONE, result, null);
        }catch (Exception e){
            this.update(Cycle.ERROR, null, e);
        }
    }

    private void update(Cycle cycle, T result, Exception e){
        this.cycle = cycle;
        if (lifecycle == null){
            return;
        }

        try {
            switch (cycle){
                case STARTED:
                    this.lifecycle.onStart(currentThread());
                    break;
                case RUNNING:
                    this.lifecycle.onRunning(currentThread());
                    break;
                case DONE:
                    this.lifecycle.onFinish(currentThread(), result);
                    break;
                case ERROR:
                    this.lifecycle.onError(currentThread(), e);
                    break;
            }
        }catch (Exception ex){
            if (cycle == Cycle.ERROR){
                throw ex;
            }
        }
    }

    @Override
    public Cycle getCycle() {
        return this.cycle;
    }
}
