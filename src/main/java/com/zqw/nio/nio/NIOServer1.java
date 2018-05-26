package com.zqw.nio.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NIOServer1 {
    private static int port = 8000;
    private  Selector selector;
    public static void main(String[] args) throws IOException {
        new NIOServer1().init();
    }
    public void init() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        listen();
    }

    private  void listen() throws IOException {
        while(true){
            selector.select();
            Iterator<SelectionKey> iterator =  selector.selectedKeys().iterator();
            while(iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                if(selectionKey.isAcceptable()){
                    handlerAccpet(selectionKey);
                }else if(selectionKey.isReadable()){
                    handlerRead(selectionKey);
                }
            }
        }
    }

    private void handlerRead(SelectionKey selectionKey) throws IOException {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer bb = ByteBuffer.allocate(1024);
        int read = socketChannel.read(bb);
        if(read > 0){
            System.out.println(new String(bb.array()));
            ByteBuffer byteBuffer = ByteBuffer.wrap("huihua".getBytes());
            socketChannel.write(byteBuffer);
        }else{
            selectionKey.cancel();
        }
    }

    private void handlerAccpet(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
    }
}
