package org.hhs.remoting.netty.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.hhs.remoting.netty.handler.model.MessageType;
import org.hhs.remoting.netty.handler.model.NettyMessage;

/**
 * @description:
 * @author: hewater
 * @create: 2018-04-01 15:56
 **/
public class LoginAuthReqHandler extends ChannelHandlerAdapter{

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(buildLoginReq());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;
        if (message.getHeader() == null && message.getHeader().getType() == MessageType.LOGIN_RESP.getaByte()){
            Byte logingResult = (Byte)message.getBody();
            if (logingResult != (byte)0){
                ctx.close();
            } else {
                System.out.println("Login is ok:" + message);
                ctx.fireChannelRead(msg);
            }
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    private NettyMessage buildLoginReq(){
        NettyMessage message = new NettyMessage();
        NettyMessage.Header header = new NettyMessage().new Header();
        header.setType(MessageType.LOGIN_REQ.getaByte());
        message.setHeader(header);
        return  message;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.fireExceptionCaught(cause);
    }


}
