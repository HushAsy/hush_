package org.hhs.remoting.netty.handler.codehandler;

import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.ByteInput;

import java.io.IOException;

/**
 * @description:
 * @author: hewater
 * @create: 2018-04-01 15:18
 **/
public class ChannelBufferByteInput implements ByteInput {
    private final ByteBuf byteBuf;

    public ChannelBufferByteInput(ByteBuf byteBuf){
        this.byteBuf = byteBuf;
    }

    public int read() throws IOException {
        if (byteBuf.isReadable()) {
            return byteBuf.readByte() & 0xff;
        }
        return -1;
    }

    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    public int read(byte[] b, int off, int len) throws IOException {
        int avaliable = available();
        if (avaliable == 0) {
            return -1;
        }
        len = Math.min(avaliable, len);
        byteBuf.readBytes(b, off, len);
        return len;
    }

    public int available() throws IOException {
        return byteBuf.readableBytes();
    }

    public long skip(long n) throws IOException {
        int readable = byteBuf.readableBytes();
        if (readable < n) {
            n = readable;
        }
        byteBuf.readerIndex((int) (byteBuf.readerIndex()+n));
        return n;
    }

    public void close() throws IOException {

    }
}
