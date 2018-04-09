package protocol.handler;


import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import protocol.model.Header;
import protocol.model.MessageType;
import protocol.model.NettyMessage;

/**
 * 心跳检测响应
 * Created by 3307 on 2016/3/5.
 */
public class HeartBeatRespHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        NettyMessage message = (NettyMessage) msg;
//        if (message.getHeader() != null
//                && message.getHeader().getType() == MessageType.HEARTBEAT_REQ.value()) {
//            System.out.println("Receive client heart beat message : ---> " + message);
//            NettyMessage heartBeat = buildHeartBeat();
//            System.out.println("Send heart beat response message to client : --->" + heartBeat);
//            ctx.writeAndFlush(heartBeat);
//        } else {
//            ctx.fireChannelRead(msg);
//        }
        System.out.println(msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("hello--hhh");
    }

    private NettyMessage buildHeartBeat() {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.HEARTBEAT_RESP.value());
        message.setHeader(header);
        return message;
    }
}
