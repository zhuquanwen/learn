package com.zqw.jvm;

public class StackSet {
    private static void set(){
        byte[] bytes = new byte[2];
        bytes[0] = 2;
    }
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000000 ; i++) {
            set();
        }
        long end = System.currentTimeMillis();
        System.out.println(Runtime.getRuntime().maxMemory());
        System.out.println(Runtime.getRuntime().freeMemory());
        System.out.println(Runtime.getRuntime().totalMemory());
        System.out.println(end - start);
    }
}
