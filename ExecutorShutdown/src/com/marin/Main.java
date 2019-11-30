package com.marin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {

        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(() -> {
            System.out.println("Running task in thread --> " + Thread.currentThread().getName());
        });

        executorShutdown(executor);

    }

    private static void executorShutdown(ExecutorService executor) {

        try {

            System.out.println("Trying to shutdown executor.");
            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);

        } catch (InterruptedException e) {
            System.out.println("Tasks interrupted.");
        } finally {

            if(!executor.isShutdown()) {
                System.out.println("Cancel non finished tasks.");
            }

            executor.shutdownNow();
            System.out.println("Executor shutdown done.");

        }

    }

}
