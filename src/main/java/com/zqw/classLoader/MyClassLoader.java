package com.zqw.classLoader;

import java.net.URL;
import java.net.URLClassLoader;

public class MyClassLoader extends URLClassLoader {
    public MyClassLoader(URL[] urls) {
        super(urls);
    }

    @Override
    public synchronized Class<?> loadClass(String name) throws ClassNotFoundException {
        Class<?> c = findLoadedClass(name);
        if(c == null && "com.zqw.classLoader.ChangeClass".equals(name)){
            c = findClass(name);
        }
        if(c == null){
            c = super.loadClass(name,false);
        }
        return c;
    }
}
