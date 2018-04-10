package org.hhs.remoting.netty.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.ScheduledFuture;
import org.hhs.remoting.netty.model.Header;
import org.hhs.remoting.netty.model.MessageType;
import org.hhs.remoting.netty.model.NettyMessage;

import java.util.concurrent.TimeUnit;

public class HeartBeatReqHandler extends ChannelHandlerAdapter {
    private volatile ScheduledFuture<?> heartBeat;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;
        if (message.getHeader() != null && message.getHeader().getType() == MessageType.LOGIN_RESP.getaByte()){
            heartBeat = ctx.executor().scheduleAtFixedRate(new HeartBeatReqHandler.HeartBeatTask(ctx), 1, 5, TimeUnit.SECONDS);
        } else if (message.getHeader() != null && message.getHeader().getType() == MessageType.HEARTBEAT_RESP.getaByte()){
            System.out.println("client receive server heart beat message:--->" + message);
        }else {
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
//        heartBeat = ctx.executor().scheduleAtFixedRate(new HeartBeatReqHandler.HeartBeatTask(ctx), 1, 5, TimeUnit.SECONDS);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (heartBeat != null){
            heartBeat.cancel(true);
            heartBeat = null;
        }
        ctx.fireExceptionCaught(cause);
    }

    private NettyMessage buildHeatBeat(){
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.HEARTBEAT_REQ.getaByte());
        message.setHeader(header);
        return message;
    }

    private class HeartBeatTask implements Runnable {
        private final ChannelHandlerContext ctx;
        public HeartBeatTask(final ChannelHandlerContext ctx){
            this.ctx = ctx;
        }

        public void run() {
            NettyMessage heatBeat = buildHeatBeat();
            System.out.println("Client send heart beat message to server:--->" + heatBeat);
            ctx.writeAndFlush(heatBeat);
        }
    }
}
