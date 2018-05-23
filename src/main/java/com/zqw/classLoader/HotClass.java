package com.zqw.classLoader;


import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class HotClass {
    private static long lastTime = 0L;
    private static String searchPath = "E:\\coding\\idea\\parallel\\out\\production\\classes\\";

    public static void reloadClass() throws MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        boolean change = checkChange(searchPath + "\\com\\zqw\\classLoader\\ChangeClass.class");
        if(change){
            MyClassLoader classLoader = new MyClassLoader(new URL[]{new URL("file:" + searchPath)});
            Class<?> clazz =  classLoader.loadClass("com.zqw.classLoader.ChangeClass");
            Object cc =  clazz.newInstance();
            Method method = clazz.getDeclaredMethod("test", null);
            method.invoke(cc, null);
        }
    }

    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException, MalformedURLException, ClassNotFoundException, InterruptedException {
        while (true){
            reloadClass();
            TimeUnit.SECONDS.sleep(2);
        }
    }

    public static boolean checkChange(String path){
        File file = new File(path);
        long newTime = file.lastModified();
        if(newTime > lastTime){
            lastTime = newTime;
            return true;
        }
        return false;
    }
}
