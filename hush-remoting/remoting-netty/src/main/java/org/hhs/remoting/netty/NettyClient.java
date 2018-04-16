package org.hhs.remoting.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.hhs.common.rpc.NetUtils;
import org.hhs.common.rpc.URL;
import org.hhs.remoting.api.Client;
import org.hhs.remoting.netty.handler.HeartBeatReqHandler;
import org.hhs.remoting.netty.handler.LoginAuthReqHandler;
import org.hhs.remoting.netty.handler.RpcClientHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NettyClient implements Client {
    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    EventLoopGroup group = new NioEventLoopGroup();
    private ChannelFuture channelFuture;

    private volatile RpcClientHandler rpcClientHandler;

    public NettyClient(URL url, ChannelHandler channelHandler){
        try {
            connect(url, channelHandler);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void connect(URL url, ChannelHandler channelHandler) throws InterruptedException {
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
                            ch.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(Thread.currentThread().getContextClassLoader())));
                            ch.pipeline().addLast(new ObjectEncoder());
                            ch.pipeline().addLast("clientHandler",new RpcClientHandler());
//                            ch.pipeline().addLast(new LoginAuthReqHandler());
//                            ch.pipeline().addLast(new HeartBeatReqHandler());
//                            ch.pipeline().addLast(this);

                        }
                    });
            channelFuture = bootstrap.connect(new InetSocketAddress(url.getHost(), url.getPort()),new InetSocketAddress(NetUtils.getLocalAddress(), 8081)).sync();
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (channelFuture.isSuccess()){
                        rpcClientHandler = (RpcClientHandler) channelFuture.channel().pipeline().get("clientHandler");
                        System.out.println("success handler");
                    }
                }
            });
        }finally {

        }
    }

    public RpcClientHandler getRpcClientHandler() {
        return rpcClientHandler;
    }

    @Override
    public ChannelFuture getChannelFuture() {
        return channelFuture;
    }
}
