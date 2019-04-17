package com.xpj.balking;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.TimeUnit;

/**
 * 可以修改并保存的数据
 */
public class Data {

    /**保存的文件名称*/
    private final String filename;
    /**数据内容*/
    private String content;
    /**修改后的内容若未保存，则为true*/
    private boolean changed;

    public Data(String filename, String content) {
        this.filename = filename;
        this.content = content;
        this.changed = true;
    }

    public synchronized  void change(String newContent){
        this.content = newContent;
        changed = true;
    }

    public synchronized void save() throws IOException {
        if (!changed){ //守护条件
            /**balking处理*/
            System.err.printf("%s calls save, but file data not changed, content is %s , changed is %s \n",
                    Thread.currentThread().getName(), content, changed);
            return;
        }

        doSave();
        changed = false;
    }

    private void doSave() throws IOException {
        System.out.println(Thread.currentThread().getName() + " calls doSave, content=" + content);
        Writer writer = new FileWriter(filename);
        writer.write(content);
        writer.close();
    }


}
