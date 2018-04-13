package org.hhs.remoting.netty.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.hhs.remoting.api.model.Header;
import org.hhs.remoting.api.model.MessageType;
import org.hhs.remoting.api.model.NettyMessage;


/**
 * @description:
 * @author: hewater
 * @create: 2018-04-01 22:07
 **/
public class HeartBeatRespHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;
        if (message.getHeader() != null && message.getHeader().getType()== MessageType.HEARTBEAT_REQ.getaByte()){
            System.out.println("receive client heart bear message:-->"+message);
            NettyMessage heartBeat = buildHearBeat();
            System.out.println("Send heart beat response message to client:-->"+heartBeat);
            ctx.writeAndFlush(heartBeat);
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    private NettyMessage buildHearBeat(){
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.HEARTBEAT_RESP.getaByte());
        message.setHeader(header);
        return message;
    }
}
