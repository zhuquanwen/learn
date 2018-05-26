package com.zqw.parallel.thread.stampLock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.StampedLock;

public class StampLock2 {
    private StampedLock lock = new StampedLock();
    private List<String> list = new ArrayList<>();

    public void add(String str){
        long stamp = 0;
        try{
            stamp = lock.writeLock();
        }finally {
            lock.unlockWrite(stamp);
        }
    }

    public List<String> get(){
        long stamp = lock.tryOptimisticRead();
        try{
            if(lock.validate(stamp)){
                return list;
            }else{
                stamp = lock.readLock();
                return list;
            }
        }finally {
            lock.unlockRead(stamp);
        }

    }
}
