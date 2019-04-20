package com.xpj.future;

public class FutureData implements Data {

    private RealData realData;
    private boolean ready = false;


    public synchronized void setRealData(RealData realData) {
        if (ready){
            return; //balk
        }

        this.realData = realData;
        this.ready = true;
        notifyAll();
    }

    @Override
    public synchronized String getContent() {

        while(!ready){
            try {
                wait();
            } catch (InterruptedException e) {
                return null;
            }
        }
        return realData.getContent();
    }
}
