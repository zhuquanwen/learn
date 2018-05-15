package com.zqw.parallel.thread.work1;

import java.util.concurrent.TimeUnit;

public class ThreadTest {
    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() ->{
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " is running");
        });
        thread1.setName("thread1");

        Thread thread2 = new Thread(() ->{
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " is running");
        });
        thread2.setName("thread2");

        Thread thread3 = new Thread(() ->{
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " is running");
        });
        thread3.setName("thread3");

        thread3.start();
        thread3.join();
        thread2.start();
        thread2.join();
        thread1.start();
//        thread3.join();
    }
}
