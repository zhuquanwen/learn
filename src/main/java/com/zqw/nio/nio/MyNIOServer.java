package com.zqw.nio.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class MyNIOServer {
    private static int serverPort = 8000;
    public static void init() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(serverPort));
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务端启动!!!");
        handlerSelector(selector);
    }

    private static void handlerSelector(Selector selector) throws IOException {
        while(true){
            selector.select();
            Set<SelectionKey> set = selector.selectedKeys();
            Iterator<SelectionKey> iterator = set.iterator();
            while(iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                if(selectionKey.isAcceptable()){
                    handdlerAccept(selectionKey);
                }else if(selectionKey.isReadable()){
                    handlerRead(selectionKey);
                }
            }
        }
    }

    private static void handlerRead(SelectionKey selectionKey) throws IOException {
//        // 服务器可读取消息:得到事件发生的Socket通道
//        SocketChannel channel = (SocketChannel) key.channel();
//        // 创建读取的缓冲区
//        ByteBuffer buffer = ByteBuffer.allocate(1024);
//        int read = channel.read(buffer);
//        if(read > 0){
//            byte[] data = buffer.array();
//            String msg = new String(data).trim();
//            System.out.println("服务端收到信息：" + msg);
//
//            //回写数据
//            ByteBuffer outBuffer = ByteBuffer.wrap("好的".getBytes());
//            channel.write(outBuffer);// 将消息回送给客户端
//        }else{
//            System.out.println("客户端关闭");
//            key.cancel();
//        }
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer bb = ByteBuffer.allocate(1024);
        int len = -1;
        if((len = socketChannel.read(bb)) != -1){
            bb.flip();
            System.out.println("接受到消息:" + new String(bb.array()));
            bb.clear();
        }else{
            selectionKey.cancel();
        }
        bb = ByteBuffer.wrap("我给你回话了".getBytes());
        socketChannel.write(bb);

    }

    private static void handdlerAccept(SelectionKey selectionKey) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        System.out.println("客户端建立连接");
        socketChannel.register(selectionKey.selector(), SelectionKey.OP_READ);
    }

    public static void main(String[] args) {
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
