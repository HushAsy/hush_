package org.hhs.remoting.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.hhs.common.rpc.URL;
import org.hhs.remoting.api.Server;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
public class NettyServer implements Server{
    private Map<String, Channel> channels = new ConcurrentHashMap();

    private ChannelFuture channelFuture;

    public NettyServer(URL url, ChannelHandler channelHandler){
        try {
            bind(url, channelHandler);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void bind(URL url, ChannelHandler channelHandler) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(Thread.currentThread().getContextClassLoader())));
                        ch.pipeline().addLast(new ObjectEncoder());
//                        ch.pipeline().addLast(new LoginAuthRespHandler());
//                        ch.pipeline().addLast(new HeartBeatRespHandler());
                        if (channelHandler != null){
                            ch.pipeline().addLast(channelHandler);
                        }
                    }
                });
        channelFuture = bootstrap.bind(url.getHost(), url.getPort()).sync();
    }

    public Map<String, Channel> getChannels() {
        return channels;
    }

    public Channel getChannl(String url) {
        return channels.get(url);
    }
}
