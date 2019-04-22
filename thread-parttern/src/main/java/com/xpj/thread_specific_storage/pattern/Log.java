package com.xpj.thread_specific_storage.pattern;

public class Log {

    private static final ThreadLocal<TSLog> tsLocal = new ThreadLocal<>();

    public static void println(String s){
        getTSLog().println(s);
    }

    public static void close(){
        getTSLog().close();
    }

    private static TSLog getTSLog(){
        TSLog tsLog = tsLocal.get();
        if(tsLog == null){
            tsLog = new TSLog(Thread.currentThread().getName() + "-log.txt");
            tsLocal.set(tsLog);
        }
        return tsLog;
    }
}
