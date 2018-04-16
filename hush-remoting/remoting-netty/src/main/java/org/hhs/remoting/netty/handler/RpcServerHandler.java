package org.hhs.remoting.netty.handler;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.hhs.remoting.api.Response;
import org.hhs.remoting.api.model.Header;
import org.hhs.remoting.api.model.MessageType;
import org.hhs.remoting.api.model.NettyMessage;
import org.hhs.remoting.netty.NettyServer;
import sun.nio.ch.Net;

/**
 * @description:
 * @author: hewater
 * @create: 2018-04-16 21:58
 **/
public class RpcServerHandler extends SimpleChannelInboundHandler<NettyMessage> {


    @Override
    protected void messageReceived(ChannelHandlerContext ctx, NettyMessage msg) throws Exception {
        if (msg.getHeader().getType() == MessageType.RPC_REQUEST.getaByte()) {
            //进行rpc调用
            NettyServer.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println(msg);
                    NettyMessage message = new NettyMessage();
                    message.setRequestId(msg.getRequestId());
                    Header header = new Header();
                    header.setType(MessageType.RPC_RESPONSE.getaByte());
                    message.setHeader(header);
                    Response response = new Response();
                    response.setName("mike");
                    response.setBody("hello every body");
                    ctx.writeAndFlush(message).addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture future) throws Exception {
                            System.out.println("send response for request:"+msg.getRequestId());
                        }
                    });
                }
            });
        }
    }
}
