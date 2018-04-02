package org.hhs.remoting.netty;

import org.junit.Test;

import java.io.IOException;

/**
 * @description:
 * @author: hewater
 * @create: 2018-04-01 23:35
 **/
public class Netty {

    @Test
    public void testHeartBeart() throws InterruptedException, IOException {
        new NettyServer().bind();
        Thread.sleep(5000);
        new NettyClient().connect("127.0.0.1", 8080);
        System.in.read();
    }
}
