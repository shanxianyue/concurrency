package com.xpj.single_thread_execution;

public class Passengers extends Thread {
    /**机场安检类*/
    private FlightSecurity flightSecurity;
    /**登机牌*/
    private String boardingPass;
    /**身份证*/
    private String idCard;

    public Passengers(FlightSecurity flightSecurity, String boardingPass, String idCard){
        this.flightSecurity = flightSecurity;
        this.boardingPass = boardingPass;
        this.idCard = idCard;
    }

    @Override
    public void run() {
        //让旅客不停的过安检
        while(true) {
            /**
             * 会修改flightSecurity的状态，这样就会产生并发问题
             * 解决：在pass方法上加 synchronized
             */
            flightSecurity.pass(boardingPass, idCard);
        }
    }

    public static void main(String[] args) {
        FlightSecurity flightSecurity = new FlightSecurity();
        new Passengers(flightSecurity, "A1", "A23323").start();
        new Passengers(flightSecurity, "B1", "B23323").start();
        new Passengers(flightSecurity, "C1", "C23323").start();
    }
}
