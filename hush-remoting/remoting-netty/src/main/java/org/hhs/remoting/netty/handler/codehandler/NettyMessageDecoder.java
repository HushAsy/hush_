package org.hhs.remoting.netty.handler.codehandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: hewater
 * @create: 2018-04-01 14:34
 **/
public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder{
    MarshallingDecoder decoder;
    public NettyMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) throws IOException {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
        decoder = new org.hhs.remoting.netty.handler.codehandler.MarshallingDecoder();
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if (frame == null){
            return null;
        }
        NettyMessage message = new NettyMessage();
        NettyMessage.Header header = new NettyMessage().new Header();
        header.setCrcCode(in.readInt());
        header.setLength(in.readInt());
        header.setType(in.readByte());
        header.setPriority(in.readByte());
        header.setSessionId(in.readLong());
        int size = in.readInt();
        if (size > 0){
            Map<String, Object> attch = new HashMap<String, Object>();
            int keySize = 0;
            byte[] keyArray = null;
            for (int i = 0; i < size; i++){
                keySize = in.readInt();
                keyArray = new byte[keySize];
                in.readBytes(keyArray);
                String key = new String(keyArray, "UTF-8");
                attch.put(key, decoder.decode(in));
                keyArray = null;
                key = null;
                header.setAttachment(attch);
            }
        }
        if (in.readableBytes() > 4){
            message.setHeader(header);
        }
        return message;
    }


}
