package org.hhs.remoting.netty.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.hhs.remoting.netty.model.MessageType;
import org.hhs.remoting.netty.model.NettyMessage;

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
        if (nettyMessage.getHeader().getType() == MessageType.REQUEST.getaByte()){
            System.out.println(nettyMessage.toString());
        } else if (nettyMessage.getHeader().getType() == MessageType.RESPONSE.getaByte()){
            System.out.println(nettyMessage.toString());
        }
    }
}
