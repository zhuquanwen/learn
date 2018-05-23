package com.zqw.design;

public class Sington {
    private Sington(){
        System.out.println("创建");
    }

    private static class SingtonHodler{
        private static Sington sington = new Sington();
    }

    public static Sington getSington(){
        return SingtonHodler.sington;
    }

    public static void main(String[] args) {
        System.out.println(getSington());
        System.out.println(getSington());
    }
}
