package com.xpj.guarded;

public class Request {
    private final String sendValue;

    public Request(String sendValue) {
        this.sendValue = sendValue;
    }

    public String getSendValue() {
        return sendValue;
    }
}
