package org.hhs.remoting.netty.handler.codehandler;

import io.netty.buffer.ByteBuf;
import org.hhs.remoting.netty.MarshallingCodeCFactory;
import org.jboss.marshalling.ByteInput;
import org.jboss.marshalling.Unmarshaller;

import java.io.IOException;

/**
 * @description:
 * @author: hewater
 * @create: 2018-04-01 15:08
 **/
public class MarshallingDecoder {
    private final Unmarshaller unmarshaller;

    public MarshallingDecoder() throws IOException {
        unmarshaller = MarshallingCodeCFactory.buildUnMarshalling();
    }

    protected Object decode(ByteBuf in){
        int objectSize = in.readInt();
        ByteBuf buf = in.slice(in.readerIndex(), objectSize);
        ByteInput input = new ChannelBufferByteInput(buf);
        Object obj = null;
        try {
            unmarshaller.start(input);
            obj = unmarshaller.readObject();
            unmarshaller.finish();
            in.readerIndex(in.readerIndex()+objectSize);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                unmarshaller.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }
}
