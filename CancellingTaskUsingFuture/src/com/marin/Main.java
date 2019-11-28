package com.marin;

import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {

        timedRun(() -> {
                    System.out.println("Thread name is --> " + Thread.currentThread().getName());}
            , 100
                , TimeUnit.SECONDS);
        }

    public static void timedRun(Runnable r, long timeout, TimeUnit unit) {

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<?> task = executorService.submit(r);

        try {
            task.get(timeout, unit);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Canceling the task.");
            task.cancel(true); // Interrupt if running
        }

    }

}
