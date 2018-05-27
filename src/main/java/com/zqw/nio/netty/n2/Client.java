package com.zqw.nio.netty.n2;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    public static void main(String[] args) {
        ClientBootstrap bootstrap = new ClientBootstrap();
        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService work = Executors.newCachedThreadPool();
        bootstrap.setFactory(new NioClientSocketChannelFactory(boss, work));
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline channelPipeline = Channels.pipeline();
                channelPipeline.addLast("encoder", new StringEncoder());
                channelPipeline.addLast("decoder", new StringDecoder());
                channelPipeline.addLast("clientHi", new ClientHiHandler());
                return channelPipeline;
            }
        });

        ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress("localhost",7777));
        Channel channel = channelFuture.getChannel();
        System.out.println("client is starting...");
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("请输入:");
            String input = scanner.next();
            channel.write(input);
        }
    }
}
