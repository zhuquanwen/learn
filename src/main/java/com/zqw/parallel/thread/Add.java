package com.zqw.parallel.thread;

public class Add {
    private int shareInt = 0;

    public int getShareInt() {
        return shareInt;
    }

    public void setShareInt(int shareInt) {
        this.shareInt = shareInt;
    }

    private int max = 100000000;

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public synchronized void add1(){
        if(shareInt <= max){
            shareInt++;
        }
    }

    public static void main(String[] args) {
        Add add = new Add();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    while (true){
                        if(add.getMax() <= add.getShareInt()){
                            System.out.println(System.currentTimeMillis() - start);
                            Thread.currentThread().stop();
                        }
                        add.add1();

                    }
                }
            };
            new Thread(r).start();
        }
    }
}
