package org.hhs.remoting.netty.handler.codehandler;

import io.netty.buffer.ByteBuf;
import org.hhs.remoting.netty.MarshallingCodeCFactory;
import org.jboss.marshalling.Marshaller;

import java.io.IOException;

/**
 * @description: 消息编码工具类
 * @author: hewater
 * @create: 2018-04-01 13:28
 **/
public class MarshallingEncoder {
    private static final byte[] LENGTH_PLACEHOLDER = new byte[4];
    Marshaller marshaller;

    public MarshallingEncoder() throws IOException {
        marshaller = MarshallingCodeCFactory.buildMarshalling();
    }

    protected void encode(Object msg, ByteBuf out) {
        try {
            int lengthPos = out.writerIndex();
            out.writeBytes(LENGTH_PLACEHOLDER);
            ChannelBufferByteOutput output = new ChannelBufferByteOutput(out);
            marshaller.start(output);
            marshaller.writeObject(msg);
            marshaller.finish();
            out.setInt(lengthPos, out.writerIndex() - lengthPos -4);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                marshaller.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
