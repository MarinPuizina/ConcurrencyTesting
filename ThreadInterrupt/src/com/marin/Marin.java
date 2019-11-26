package com.marin;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Marin implements Runnable {

    public int marinCount = 1;

    @Override
    public void run() {

        for(int i=1; i<=5; i++) {

            while(!Thread.currentThread().isInterrupted()) {

                getTime();
                System.out.println(Thread.currentThread().getName() + " --> Says, Marin is the best: " + marinCount++);

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    getTime();
                    System.out.println(Thread.currentThread().getName() + " --> Marin has interrupted the thread.");
                    cancel();
                    System.out.println("\t\t\t\t\t--> Interrupt status: " + Thread.currentThread().isInterrupted());
                }

            }

        }

    }

    public void cancel() {
        Thread.currentThread().interrupt();
    }

    public static void getTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.print(dtf.format(now) + " - ");
    }
}
