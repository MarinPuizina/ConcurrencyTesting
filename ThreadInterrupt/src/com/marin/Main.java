package com.marin;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Marin ma = new Marin();

        Thread t1 = new Thread(ma);
        t1.start();
        Thread.sleep(6000);
        t1.interrupt();
        t1.join();

        Marin.getTime();
        System.out.println(Thread.currentThread().getName() + " --> Ending the process.");

    }
}
