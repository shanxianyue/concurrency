package com.xpj.two_phase_termination.example2;

public abstract class AbstractTerminatableThread extends Thread implements Terminatable{

    public final TerminationToken terminationToken;

    public AbstractTerminatableThread() {
        this(new TerminationToken());
    }

    public AbstractTerminatableThread(TerminationToken terminationToken) {
        super();
        this.terminationToken = terminationToken;
        terminationToken.register(this);
    }

    /**
     * 留给子类实现其线程处理逻辑
     * @throws Exception
     */
    protected abstract void doRun() throws Exception;

    /**
     * 留给子类实现。用于实现线程停止后的一些清理动作
     * @param cause
     */
    protected void doCleanup(Exception cause){
        //do nothing
    }

    /**
     * 留给子类实现。用于执行线程停止所需的操作
     */
    protected void doTerminate(){
        //do nothing
    }

    @Override
    public void run() {
        Exception ex = null;
        try {
            while(true){
                if (terminationToken.isToShutdown() && terminationToken.reservation.get() <= 0){
                    break;
                }
                doRun();
            }
        } catch (Exception e) {
            ex = e;
        }finally {
            try {
                doCleanup(ex);
            } finally {
                terminationToken.notifyThreadTermination(this);
            }
        }
    }

    @Override
    public void interrupt() {
        terminate();
    }

    @Override
    public void terminate(){
        terminationToken.setToShutdown(true);
        try {
            doTerminate();
        } finally {
            /**若无待处理的任务，则试图强制终止线程*/
            if (terminationToken.reservation.get() <= 0){
                super.interrupt();
            }
        }
    }

    public void terminate(boolean waitUtilThreadTerminated){
        terminate();
        if (waitUtilThreadTerminated){
            try {
                this.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
