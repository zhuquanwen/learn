package com.zqw.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class NIOMappingTest {
    public static void main(String[] args) throws IOException {
        RandomAccessFile file = new RandomAccessFile("D:/a.txt","rw");
        FileChannel fileChannel = file.getChannel();
        MappedByteBuffer mbb = fileChannel.map(FileChannel.MapMode.READ_WRITE,0,file.length());
        while(mbb.hasRemaining()){
            System.out.print(mbb.get());
        }
        mbb.put(0,(byte)98);
        file.close();
    }
}
