package org.hhs.remoting.netty.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.hhs.remoting.netty.handler.model.MessageType;
import org.hhs.remoting.netty.handler.model.NettyMessage;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LoginAuthRespHandler extends ChannelHandlerAdapter {

    private Map<String, Boolean> nodeCheck = new ConcurrentHashMap<String, Boolean>();
    private String[] whiteList = {"127.0.0.1", "192.168.0.1"};

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;
        if (message.getHeader() != null && message.getHeader().getType() == MessageType.LOGIN_REQ.getaByte()){
            String nodeIndex = ctx.channel().remoteAddress().toString();
            NettyMessage loginResp = null;
            if (nodeCheck.containsKey(nodeIndex)){
                loginResp = buildResponse((byte)-1);
            } else {
                InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
                String ip = address.getAddress().getHostAddress();
                boolean isOk = false;
                for (String wip : whiteList){
                    if (wip.equals(ip)){
                        isOk = true;
                        break;
                    }
                }
                loginResp = isOk ? buildResponse((byte) 0):buildResponse((byte)-1);
                if (isOk){
                    nodeCheck.put(nodeIndex, true);
                }
            }
            System.out.println("the login response is :" + loginResp + "body[" + loginResp.getBody() +"]");
            ctx.writeAndFlush(loginResp);
        }else {
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        nodeCheck.remove(ctx.channel().remoteAddress().toString());
        ctx.close();
        ctx.fireExceptionCaught(cause);
    }

    private NettyMessage buildResponse(byte result){
        NettyMessage message = new NettyMessage();
        NettyMessage.Header header = new NettyMessage().new Header();
        header.setType(MessageType.LOGIN_RESP.getaByte());
        message.setHeader(header);
        message.setBody(result);
        return message;
    }
}
