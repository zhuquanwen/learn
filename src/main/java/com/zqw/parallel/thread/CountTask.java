package com.zqw.parallel.thread;

import java.util.ArrayList;
import java.util.concurrent.RecursiveTask;

public class CountTask extends RecursiveTask<Long> {
    private Integer start;
    private Integer end;
    private Integer t = 1000;
    public CountTask(Integer start, Integer end){
        this.start = start;
        this.end = end;
    }
    @Override
    protected Long compute() {
        Long sum = 0L;
        if(end - start < t){
            for(int i = start ; i<= end ; i++){
                sum += i;
            }
        }else{
            Integer step = (end + start)/100;
            ArrayList<CountTask> countTaskArrayList = new ArrayList<>();

        }
        return null;
    }
}
