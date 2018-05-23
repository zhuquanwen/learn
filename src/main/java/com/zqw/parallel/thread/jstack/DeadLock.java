package com.zqw.parallel.thread.jstack;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLock {
    static class A{
        Object obj1 = new Object();
        Object obj2 = new Object();
    }
    public static void main(String[] args) {
        A a = new A();
        Runnable r1 = () ->{
            synchronized (a.obj1){
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (a.obj2){
                    System.out.println(1111111);
                }
            }
        };
        Runnable r2 = () ->{
            synchronized (a.obj2){
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (a.obj1){
                    System.out.println(2222222);
                }
            }
        };
        Thread thread1 = new Thread(r1);
        Thread thread2 = new Thread(r2);
        thread1.start();
        thread2.start();


    }
}
