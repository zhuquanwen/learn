package com.zqw.nio.netty.n1;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server1 {
    public static void main(String[] args) {
        ServerBootstrap bootstrap = new ServerBootstrap();
        ExecutorService work = Executors.newCachedThreadPool();
        ExecutorService boss = Executors.newCachedThreadPool();
        bootstrap.setFactory(new NioServerSocketChannelFactory(boss, work));
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline pipeline = Channels.pipeline();
                pipeline.addLast("decoder", new StringDecoder());
                pipeline.addLast("encoder", new StringEncoder());
                pipeline.addLast("helloHandler", new HelloHandler1());
                return pipeline;
            }
        });
        bootstrap.bind(new InetSocketAddress(8000));

    }
}
