package com.zqw.parallel.thread;

import java.util.concurrent.*;

public class ThreadPool {
    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(3);
        es.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println(1111);
            }
        });

        ExecutorService es2 = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(),
                Executors.defaultThreadFactory(), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

            }
        }){
            @Override
            protected void beforeExecute(Thread t, Runnable r){
                System.out.println(111111);
            }
        };
        es2.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println(22222);
            }
        });

    }
}
