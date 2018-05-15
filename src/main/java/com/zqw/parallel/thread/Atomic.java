package com.zqw.parallel.thread;

import jdk.internal.org.objectweb.asm.TypeReference;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class Atomic {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(10);
        System.out.println(atomicInteger.incrementAndGet());

        AtomicReference<String> atomicReference = new AtomicReference<>("22222");
        atomicReference.getAndUpdate((x) ->{
            return x + "111";
        });
        atomicReference.accumulateAndGet("zhu",(x, u)->{
            return u + x;
        });

        AtomicStampedReference<Integer> stampedReference = new AtomicStampedReference<>(10,0);
        stampedReference.compareAndSet(10,11,1,2);

        System.out.println(atomicReference.get());
        System.out.println(stampedReference.getReference());
    }
}
