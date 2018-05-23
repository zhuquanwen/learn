package com.zqw.parallel.thread.stampLock;

import javax.print.DocFlavor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.StampedLock;

public class StampLockTest {
    private static StampedLock lock = new StampedLock();
    private static Long shareLong = 0L;
    void add(){
        long stamp = 0;
        try{
            stamp = lock.writeLock();
            System.out.println("write:" + ++shareLong);

        }finally {
            lock.unlockWrite(stamp);
        }
    }
    long read(){
        long stamp = 0;
        long result = 0;
        try{
            stamp = lock.tryOptimisticRead();
            result = shareLong;
            if(!lock.validate(stamp)){
                stamp = lock.readLock();
            }
            System.out.println("read:" + shareLong);
        }finally {
            lock.unlockRead(stamp);
        }
        return result;
    }

    public static void main(String[] args) {
        StampLockTest stampLockTest = new StampLockTest();
        ExecutorService es = Executors.newCachedThreadPool();
        for (int i = 0; i < 500; i++) {
            final int x = i;
            es.submit(() ->{
                if( x%2 == 0){
                    stampLockTest.add();
                }else {
                    stampLockTest.read();
                }

            });
        }
        es.shutdown();
    }
}
