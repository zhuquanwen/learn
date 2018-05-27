package com.zqw.nio.netty.n1;



import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 7777);
        socket.getOutputStream().write("i am zhu".getBytes());
        byte[] bytes = new byte[1024];
        socket.getInputStream().read(bytes);
        System.out.println(new String(bytes));
        socket.close();
    }
}
