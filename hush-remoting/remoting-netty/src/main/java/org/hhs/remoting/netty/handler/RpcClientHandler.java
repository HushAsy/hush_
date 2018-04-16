package org.hhs.remoting.netty.handler;

import io.netty.channel.*;
import org.hhs.remoting.api.RpcFuture;
import org.hhs.remoting.api.model.Header;
import org.hhs.remoting.api.model.MessageType;
import org.hhs.remoting.api.model.NettyMessage;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: hewater
 * @create: 2018-04-12 22:32
 **/
public class RpcClientHandler extends SimpleChannelInboundHandler<NettyMessage> {

    private volatile Channel channel;
    private ConcurrentHashMap<String, RpcFuture> pendingRPC = new ConcurrentHashMap();
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        this.channel = ctx.channel();
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, NettyMessage msg) throws Exception {
        if (msg.getHeader().getType() == MessageType.RPC_REQUEST.getaByte()){
            //请求
        } else if(msg.getHeader().getType() == MessageType.RPC_RESPONSE.getaByte()){
            //响应
            RpcFuture rpcFuture = null;
            if ((rpcFuture = pendingRPC.get(msg.getRequestId())) != null){
                pendingRPC.remove(msg.getRequestId());
                rpcFuture.setResult(msg);
            }
            System.out.println(msg);
        }
    }

    public RpcFuture sendRequest(NettyMessage nettyMessage){
        RpcFuture rpcFuture = new RpcFuture(nettyMessage);
        pendingRPC.put(nettyMessage.getRequestId(), rpcFuture);
        this.channel.writeAndFlush(nettyMessage);
        return rpcFuture;
    }

}
