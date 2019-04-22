package com.xpj.thread_specific_storage.pattern;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class TSLog {
    private PrintWriter writer;

    public TSLog(String filename){
        try {
            writer = new PrintWriter(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void println(String s){
        writer.println(s);
    }

    public void close(){
        writer.println("=== End of log ===");
        writer.close();
    }
}
