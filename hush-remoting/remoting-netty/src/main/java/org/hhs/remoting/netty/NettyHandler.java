package org.hhs.remoting.netty;

import io.netty.channel.*;
import org.hhs.common.rpc.NetUtils;
import org.hhs.common.rpc.URL;
import org.hhs.remoting.api.model.MessageType;
import org.hhs.remoting.api.model.NettyMessage;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: hewater
 * @create: 2018-04-10 22:50
 **/
public class NettyHandler extends ChannelHandlerAdapter{

    private final Map<String, Channel> channels = new ConcurrentHashMap();

    private volatile Channel channel;

    private final URL url;

    public NettyHandler(URL url, ChannelHandler handler){
        if (url == null){
            throw new IllegalArgumentException("url is null");
        }
        if (handler == null){
            throw new IllegalArgumentException("handler is null");
        }
        this.url = url;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        NettyMessage nettyMessage = (NettyMessage) msg;
        if (nettyMessage.getHeader().getType() == MessageType.RPC_REQUEST.getaByte()){

        } else if(nettyMessage.getHeader().getType() == MessageType.RPC_RESPONSE.getaByte()) {

        }else{

        }
    }

    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        super.connect(ctx, remoteAddress, localAddress, promise);
        this.channel = ctx.channel();
        channels.put(NetUtils.getAddressStr((InetSocketAddress)remoteAddress), ctx.channel());
    }

    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        super.disconnect(ctx, promise);
        channels.remove(NetUtils.getAddressStr((InetSocketAddress)ctx.channel().remoteAddress()));
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        super.close(ctx, promise);
        channels.forEach((k, channel)->{
            channel.close();
        });
    }

    public Channel getChannel() {
        return channel;
    }

    public Channel getCurrentChannel(String str){
        return channels.get(str);
    }
}

