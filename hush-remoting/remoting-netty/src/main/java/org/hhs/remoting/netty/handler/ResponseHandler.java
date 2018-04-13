package org.hhs.remoting.netty.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.hhs.remoting.api.model.Header;
import org.hhs.remoting.api.model.MessageType;
import org.hhs.remoting.api.model.NettyMessage;

/**
 * @description:
 * @author: hewater
 * @create: 2018-04-12 22:32
 **/
public class ResponseHandler extends ChannelHandlerAdapter {

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        NettyMessage nettyMessage = (NettyMessage) msg;
        if (nettyMessage.getHeader().getType() == MessageType.RPC_REQUEST.getaByte()){
            System.out.println("client-request" + nettyMessage.toString());
            ctx.channel().writeAndFlush(buildResponseMessage());
        } else if (nettyMessage.getHeader().getType() == MessageType.RPC_RESPONSE.getaByte()){
            System.out.println("client-response" + nettyMessage.toString());
        }
    }

    private static NettyMessage buildResponseMessage(){
        NettyMessage nettyMessage = new NettyMessage();
        nettyMessage.setHeader(new Header().setCrcCode(1).setLength(3).setType((byte) 2));
        String string = "server";
        nettyMessage.setBody(string);
        return nettyMessage;
    }
}
