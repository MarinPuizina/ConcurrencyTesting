package com.marin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class MyTaskService {

    private static final long TIMEOUT = 500;
    private static final TimeUnit UNIT = MILLISECONDS;

    private volatile TrackingExecutor executor;
    private final Set<String> stringsToProcess = new HashSet<>();
    private final ConcurrentMap<String, Boolean> seen = new ConcurrentHashMap<String, Boolean>();
    private List<String> strList = new ArrayList<>();

    public MyTaskService(String str) {
        stringsToProcess.add(str);
    }

    public synchronized void start() {

       executor = new TrackingExecutor(Executors.newCachedThreadPool());

       for (String str : stringsToProcess) {
           submitMyTask(str);
       }

       stringsToProcess.clear();
       
    }

    public synchronized void stop() throws InterruptedException {

       try {
           saveNotFinishedTasks(executor.shutdownNow());
           if (executor.awaitTermination(TIMEOUT, UNIT)) {
               saveNotFinishedTasks(executor.getCancelledTasks());
           }
       } finally {
           executor = null;
       }

    }

    private void saveNotFinishedTasks(List<Runnable> tasks) {

        for (Runnable task : tasks) {
            stringsToProcess.add(task.toString());
        }

    }

    private void submitMyTask(String str) {
        executor.execute(new MyTask(str));
    }

    private class MyTask implements Runnable {

        private final String rng;
        private int count = 1;


        MyTask(String url) {
            this.rng = url;
        }

        boolean finishedTasks() {
            return seen.putIfAbsent(rng, true) != null;
        }

        void markNotFinishedTasks() {

            seen.remove(rng);
            System.out.printf("marking %s not finished %n", rng);

        }

        public void run() {

            for (String link : strList) {
                if (Thread.currentThread().isInterrupted()) {
                    return;
                }

                submitMyTask(link);
            }

        }

        public String getRng() {
            return rng;
        }
    }

}
