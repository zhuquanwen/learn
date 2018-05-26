package com.zqw.parallel.thread;

import java.util.concurrent.atomic.AtomicInteger;

public class Add2 {
    private AtomicInteger shareInt = new AtomicInteger(0);
    private int max = 100000000;

    public AtomicInteger getShareInt() {
        return shareInt;
    }

    public void setShareInt(AtomicInteger shareInt) {
        this.shareInt = shareInt;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void add(){
        shareInt.incrementAndGet();
    }

    public static void main(String[] args) {
        Add2 add = new Add2();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    while (true){
                        if(add.getShareInt().get() >= add.getMax()){
                            System.out.println(System.currentTimeMillis() - start);
                            Thread.currentThread().stop();
                        }
                        add.add();

                    }
                }
            };
            new Thread(r).start();
        }
    }
}
