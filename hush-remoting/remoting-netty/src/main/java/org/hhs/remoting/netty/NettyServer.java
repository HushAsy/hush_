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
import org.hhs.common.rpc.NetUtils;
import org.hhs.common.rpc.URL;
import org.hhs.remoting.api.Server;
import org.hhs.remoting.netty.handler.HeartBeatRespHandler;
import org.hhs.remoting.netty.handler.LoginAuthRespHandler;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NettyServer extends ChannelHandlerAdapter implements Server{
    private Map<String, Channel> channels = new ConcurrentHashMap();

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
                        ch.pipeline().addLast(new LoginAuthRespHandler());
                        ch.pipeline().addLast(new HeartBeatRespHandler());
                        ch.pipeline().addLast(channelHandler);
                    }
                });
        ChannelFuture future = bootstrap.bind(url.getHost(), url.getPort()).sync();
        future.channel().closeFuture().sync();
    }

    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        super.connect(ctx, remoteAddress, localAddress, promise);
        if (ctx.channel() != null){
            channels.put(NetUtils.getAddressStr((InetSocketAddress)ctx.channel().remoteAddress()), ctx.channel());
        }
    }

    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        super.disconnect(ctx, promise);
        channels.remove(NetUtils.getAddressStr((InetSocketAddress)ctx.channel().remoteAddress()));
    }

    @Override
    public Map<String, Channel> getChannels() {
        return channels;
    }

    @Override
    public Channel getChannl(InetSocketAddress remoteAddress) {
        return channels.get(remoteAddress);
    }
}
