package com.zqw.nio.nio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketClient {
    final static CountDownLatch cdl = new CountDownLatch(20);
    public static void main(String[] args) {
        Runnable r = () -> {
            try {
                try {
                    cdl.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Socket socket = new Socket("127.0.0.1",8000);
                OutputStream os = socket.getOutputStream();
                os.write(("你好啊" + Thread.currentThread().getId()).getBytes());
                os.flush();
//            os.close();
                InputStream is = socket.getInputStream();
                byte[] bytes = new byte[1024];
                int len = -1;
                while((len = is.read(bytes)) != -1){
                    System.out.println(new String(bytes));
                    bytes = new byte[1024];
                }
                System.out.println(1111111);
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        ExecutorService es = Executors.newFixedThreadPool(20);
        for (int i = 0; i< 20; i++){
            es.execute(r);
            cdl.countDown();
        }

        es.shutdown();

    }
}
