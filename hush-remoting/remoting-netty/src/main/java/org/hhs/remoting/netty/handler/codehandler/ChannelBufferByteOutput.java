package org.hhs.remoting.netty.handler.codehandler;

import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.ByteOutput;

import java.io.IOException;

/**
 * @description:
 * @author: hewater
 * @create: 2018-04-01 14:21
 **/
public class ChannelBufferByteOutput implements ByteOutput {
    private final ByteBuf buf;

    public ChannelBufferByteOutput(ByteBuf bb){
        this.buf = bb;
    }

    public void write(int i) throws IOException {
        buf.writeByte(i);
    }

    public void write(byte[] bytes) throws IOException {
        buf.writeBytes(bytes);
    }

    public void write(byte[] bytes, int i, int i1) throws IOException {
        buf.writeBytes(bytes, i, i1);
    }

    public void close() throws IOException {
    }

    public void flush() throws IOException {

    }

    ByteBuf getBuf() {
        return buf;
    }
}
