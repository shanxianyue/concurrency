package com.xpj.single_thread_execution;

/**机场安检类*/
public class FlightSecurity {

    private int counter = 0;
    /**登机牌*/
    private String boardingPass = null;
    /**身份证*/
    private String idCard = null;

    public synchronized void pass(String boardingPass, String idCard){
        this.boardingPass = boardingPass;
        this.idCard = idCard;
        this.counter++;
        check();
    }

    private void check(){
        if (boardingPass.charAt(0) != idCard.charAt(0)){
            throw new RuntimeException("==身份有误==");
        }
    }

    @Override
    public String toString() {
        return "FlightSecurity{" +
                "counter=" + counter +
                ", boardingPass='" + boardingPass + '\'' +
                ", idCard='" + idCard + '\'' +
                '}';
    }
}
