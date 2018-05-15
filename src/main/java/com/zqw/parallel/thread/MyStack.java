package com.zqw.parallel.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class MyStack<E> {
    private AtomicReferenceArray<E> stack = new AtomicReferenceArray<E>(1000);
    private AtomicInteger currentSize = new AtomicInteger(-1);
    public E get(){
        return stack.getAndSet(currentSize.getAndDecrement(), null );
    }
    public void put(E e){
        stack.set(currentSize.incrementAndGet(), e);
    }

    public static void main(String[] args) {
        ArrayList<Integer> stack = new ArrayList<>();
        MyStack<Integer> myStack = new MyStack<>();
        for (int i = 0; i < 1000; i++) {
            myStack.put(i);
            stack.add(i);
        }



        Runnable r = new Runnable() {
            @Override
            public void run() {
                while(true){
                    System.out.println(Thread.currentThread().getId() + ":" + myStack.get());
                    try {
                        TimeUnit.MILLISECONDS.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };

        Runnable r1= new Runnable() {
            @Override
            public void run() {
                while(true){
                    System.out.println(Thread.currentThread().getId() + ":" + stack.get(stack.size() - 1));
                    try {
                        TimeUnit.MILLISECONDS.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    stack.remove(stack.size() - 1);
                }

            }
        };
//        ExecutorService es = Executors.newCachedThreadPool();
//        es.execute(r);
//        es.execute(r);
//        es.execute(r);
//        es.shutdown();
        ExecutorService es = Executors.newCachedThreadPool();
        es.execute(r1);
        es.execute(r1);
        es.execute(r1);
        es.shutdown();
    }
}
