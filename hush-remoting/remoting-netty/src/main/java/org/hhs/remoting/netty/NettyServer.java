package org.hhs.remoting.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.hhs.remoting.netty.handler.HeartBeatRespHandler;
import org.hhs.remoting.netty.handler.LoginAuthRespHandler;

public class NettyServer {

    public void bind() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
//                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(Thread.currentThread().getContextClassLoader())));
                        ch.pipeline().addLast(new ObjectEncoder());
                        ch.pipeline().addLast(new LoginAuthRespHandler());
                        ch.pipeline().addLast(new HeartBeatRespHandler());
                    }
                });
        ChannelFuture future = bootstrap.bind("127.0.0.1", 8080).sync();
        future.channel().closeFuture().sync();
        System.out.println("Netty server start ok");
    }

    public static void main(String...args) throws InterruptedException {
        NettyServer nettyServer = new NettyServer();
        nettyServer.bind();
    }
}
