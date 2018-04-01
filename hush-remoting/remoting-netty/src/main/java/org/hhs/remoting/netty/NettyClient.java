package org.hhs.remoting.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.hhs.remoting.netty.handler.HeartBeatRespHandler;
import org.hhs.remoting.netty.handler.codehandler.NettyMessageDecoder;
import org.hhs.remoting.netty.handler.codehandler.NettyMessageEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NettyClient {
    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    EventLoopGroup group = new NioEventLoopGroup();

    public void connect(String host, int port) throws InterruptedException {
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyMessageDecoder(1024*1024,4,4));
                            ch.pipeline().addLast(new NettyMessageEncoder());
                            ch.pipeline().addLast("ReadTimeoutHandler", new ReadTimeoutHandler(5));
                            ch.pipeline().addLast("HeartBeathandler", new HeartBeatRespHandler());
                        }
                    });
            ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port),new InetSocketAddress("127.0.0.1", 8081)).sync();
            System.out.println("connect success!");
            future.channel().closeFuture().sync();
        }finally {
            executorService.execute(new Runnable() {
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(5);
                        connect("127.0.0.1", 8080);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
