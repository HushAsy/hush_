package org.hhs.remoting.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.hhs.remoting.netty.handler.HeartBeatRespHandler;
import org.hhs.remoting.netty.handler.codehandler.NettyMessageDecoder;
import org.hhs.remoting.netty.handler.codehandler.NettyMessageEncoder;

public class NettyServer {

    public void bind() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new NettyMessageDecoder(1024*1024, 4, 4));
                        ch.pipeline().addLast(new NettyMessageEncoder());
                        ch.pipeline().addLast("ReadTimeoutHandler", new ReadTimeoutHandler(5));
                        ch.pipeline().addLast("HeartBeathandler", new HeartBeatRespHandler());
                    }
                });
        bootstrap.bind("127.0.0.1", 8080).sync();
        System.out.println("Netty server start ok");
    }

}
