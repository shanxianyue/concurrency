package com.xpj.guarded;

import java.util.LinkedList;

public class RequestQueue {
    private LinkedList<Request> queue = new LinkedList<>();

    public Request takeRequest(){
        synchronized (queue){
            while(queue.isEmpty()){
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    return null;
                }
            }
            Request request = queue.removeFirst();
            System.out.println("server take request " + request.getSendValue());
            return request;
        }
    }

    public void putRequest(Request request){
        synchronized (queue){
            System.out.println("client put request " + request.getSendValue());
            queue.addLast(request);
            queue.notifyAll();
        }
    }
}
