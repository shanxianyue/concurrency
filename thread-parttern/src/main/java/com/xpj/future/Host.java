package com.xpj.future;

public class Host {
    public Data request(int count, char c){
        System.out.printf("request(%d, %c) BEGIN \n", count, c);

        FutureData futureData = new FutureData();

        new Thread(() -> {
            RealData realData = new RealData(count, c);
            futureData.setRealData(realData);
        }).start();

        System.out.printf("request(%d, %c) END \n", count, c);

        return futureData;
    }
}
