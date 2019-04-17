package com.xpj.balking;

import java.io.IOException;

/**
 * 用于定期保存数据内容
 */
public class SaverThread extends Thread {
    private final Data data;
    public SaverThread(String name, Data data){
        super(name);
        this.data = data;
    }

    @Override
    public void run() {
        try {
            while(true){
                data.save();
                Thread.sleep(500);
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
