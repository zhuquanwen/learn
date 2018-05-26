package com.zqw.nio.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NIOServer2 {
    private static int port = 8000;
    private Selector selector;
    public void init() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);
        selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        listen();
    }

    private void listen() throws IOException {
        while(true){
            selector.select();
            Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
            while(keys.hasNext()){
                SelectionKey selectionKey = keys.next();
                keys.remove();
                if(selectionKey.isValid() && selectionKey.isAcceptable()){
                    handlerAccept(selectionKey);
                }else if(selectionKey.isValid() && selectionKey.isReadable()){
                    handlerRead(selectionKey);
                }
            }

        }
    }

    private void handlerRead(SelectionKey selectionKey) throws IOException {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer bb = ByteBuffer.allocate(1024);
        int read = -1;
        try{
            read = socketChannel.read(bb);
        }catch (Exception e){
            selectionKey.cancel();
            socketChannel.socket().close();
            socketChannel.close();
        }

        if(read < 0){
            selectionKey.cancel();
        }else{
            System.out.println("接受到消息:" + new String(bb.array()));
            bb = ByteBuffer.wrap("回你个消息".getBytes());
            socketChannel.write(bb);
            socketChannel.socket().close();
            socketChannel.close();
        }

    }

    private void handlerAccept(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel sc = serverSocketChannel.accept();
        sc.configureBlocking(false);
        sc.register(selector, SelectionKey.OP_READ);
    }

    public static void main(String[] args) throws IOException {
        new NIOServer2().init();
    }
}
