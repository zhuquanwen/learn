package com.zqw.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;

public class NIOFileTest {
    public static void main(String[] args) throws IOException {
        InputStream is = new FileInputStream(new File("C:/1.jpg"));
        Channel rchannel = ((FileInputStream) is).getChannel();
        OutputStream os = new FileOutputStream(new File("D:/1.jpg"));
        Channel wchannel = ((FileOutputStream) os).getChannel();
        ByteBuffer bb = ByteBuffer.allocate(1024);
        int len;
        while(true){
            bb.clear();
            len = ((FileChannel) rchannel).read(bb);
            if(len == -1) break;
            bb.flip();
            ((FileChannel) wchannel).write(bb);
        }
        rchannel.close();
        wchannel.close();
    }
}
