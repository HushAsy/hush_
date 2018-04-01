package org.hhs.remoting.netty.handler.codehandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;

import java.util.List;

/**
 * @description: netty解码
 * @author: hewater
 * @create: 2018-04-01 13:12
 **/
public class NettyMessageEncoder extends MessageToMessageEncoder<NettyMessage> {
    MarshallingEncoder marshallingEncoder;

    public NettyMessageEncoder(){
//        this.marshallingEncoder = new MarshallingEncoder();
    }
    protected void encode(ChannelHandlerContext channelHandlerContext, NettyMessage nettyMessage, List<Object> list) throws Exception {

    }
}
