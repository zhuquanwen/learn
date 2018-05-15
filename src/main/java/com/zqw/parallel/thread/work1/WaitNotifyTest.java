package com.zqw.parallel.thread.work1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class WaitNotifyTest {
    private int size;
    public WaitNotifyTest(int size){
        this.size = size;
    }
    private List list = new LinkedList();
    public synchronized boolean add(Object obj){
        if(list.size() >= size){
            try {
                System.out.println("add is waiting!!!");
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        list.add(obj);
        System.out.println("nodify get ....");
        this.notify();
        return true;
    }
    public synchronized Object get(){
        if(list.size() == 0){
            try {
                System.out.println("get is waiting...");
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Object obj = list.remove(0);
        System.out.println("notify add");
        this.notify();
        return obj;
    }

    public static void main(String[] args) {
        WaitNotifyTest wnt = new WaitNotifyTest(2);
        Thread thread1 = new Thread(()->{
            while (true){
                wnt.add(Math.random());
                wnt.add(Math.random());
                wnt.add(Math.random());
                System.out.println("add thread number");
            }
        });
        Thread thread2 = new Thread(()->{
            while (true){
                Object obj = wnt.get();
                System.out.println("get one number");
            }
        });
        thread1.start();
        thread2.start();
    }
}
