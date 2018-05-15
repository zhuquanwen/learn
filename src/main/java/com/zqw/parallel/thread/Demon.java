package com.zqw.parallel.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.LockSupport;

public class Demon {
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    System.out.print(11111);
                }
            }
        });
//        thread.setDaemon(true);
        thread.start();
        thread.setDaemon(true);

    }
}
