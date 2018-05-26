package com.zqw.nio.socket;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost",8000);
        OutputStream os = socket.getOutputStream();
        os.write("I AM zhuquanwen".getBytes());
        os.close();
    }
}
