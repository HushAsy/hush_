package org.hhs.remoting.netty.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.hhs.remoting.api.model.MessageType;
import org.hhs.remoting.api.model.NettyMessage;

/**
 * @description:
 * @author: hewater
 * @create: 2018-04-12 22:31
 **/
@ChannelHandler.Sharable
public class RequestHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        NettyMessage nettyMessage = (NettyMessage) msg;
        if (nettyMessage.getHeader().getType() == MessageType.RPC_REQUEST.getaByte()){
            System.out.println("server-request"+nettyMessage.toString());
        } else if (nettyMessage.getHeader().getType() == MessageType.RPC_RESPONSE.getaByte()){
            System.out.println("server-response"+nettyMessage.toString());
        }
    }
}
