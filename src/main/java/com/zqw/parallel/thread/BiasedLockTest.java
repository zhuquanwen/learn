package com.zqw.parallel.thread;

public class BiasedLockTest {
    public static void main(String[] args) throws InterruptedException {
//        -XX:+UseBiasedLocking
        Thread.currentThread().sleep(5000);
        long start = System.currentTimeMillis();
        int x = 0;
        for (int i=0; i<10000000; i++){
            synchronized (BiasedLockTest.class){
                x++;
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("共耗时：" + (end - start) + "ms");
    }

}
