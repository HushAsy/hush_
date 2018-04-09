package org.hhs.remoting.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledHeapByteBuf;
import org.hhs.remoting.netty.handler.codehandler.MessageType;
import org.hhs.remoting.netty.handler.codehandler.NettyMessage;
import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @description:
 * @author: hewater
 * @create: 2018-04-01 23:35
 **/
public class Netty {

    @Test
    public void testHeartBeart() throws InterruptedException, IOException {
//        NettyServer nettyServer = new NettyServer();
//        nettyServer.bind();
//
//        NettyClient client = new NettyClient();
//        client.connect("127.0.0.1", 8080);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("hello");
            }
        };
        runnable.run();
        System.out.println("hello1");
    }
}
