package com.xpj.two_phase_termination.example2;

/**
 * 告警功能入口类
 * 模式角色：Two-Phase Termination.ThreadOwner
 */
public class AlarmMgr {

    /**保存AlarmMgr类的唯一实例*/
    private static final AlarmMgr INSTANCE = new AlarmMgr();

    private volatile boolean shutdownRequested = false;


}
