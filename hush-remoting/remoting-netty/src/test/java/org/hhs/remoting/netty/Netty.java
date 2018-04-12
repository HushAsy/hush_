package org.hhs.remoting.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.hhs.common.rpc.NetUtils;
import org.hhs.common.rpc.URL;
import org.hhs.remoting.api.Client;
import org.hhs.remoting.api.Server;
import org.hhs.remoting.netty.handler.ResponseHandler;
import org.hhs.remoting.netty.model.Header;
import org.hhs.remoting.netty.model.NettyMessage;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * @description:
 * @author: hewater
 * @create: 2018-04-01 23:35
 **/
public class Netty {

    @Test
    public void testHeartBeart() throws InterruptedException, IOException {
        NettyTransporter transporter = new NettyTransporter();
        String urlServerTest = "dubbo://admin:hello1234@"+ NetUtils.getLocalAddress()+":20880/context/path?ad=jj";
        URL urlServer = URL.valueOf(urlServerTest);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Server server = transporter.bind(urlServer, null);
            }
        });
        t1.start();
        Thread.sleep(3000);
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                Client client = transporter.connect(urlServer, null);
            }
        });
        t2.start();
        System.in.read();
    }

    public static void main(String...args) throws InterruptedException, IOException {
        NettyTransporter transporter = new NettyTransporter();
        String urlServerTest = "dubbo://admin:hello1234@"+ NetUtils.getLocalAddress()+":20880/context/path?ad=jj";
        URL urlServer = URL.valueOf(urlServerTest);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Server server = transporter.bind(urlServer, new ResponseHandler());
//                Channel channel = server.getChannl(urlServer.getHost()+":"+urlServer.getPort());
//                ChannelFuture channelFuture = channel.closeFuture().addListener(new GenericFutureListener<Future<? super Void>>() {
//                    @Override
//                    public void operationComplete(Future<? super Void> future) throws Exception {
//                        Object object = future.get();
//                        System.out.println(object);
//                    }
//                });
            }
        });
        t1.start();
        Thread.sleep(3000);
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                Client client = transporter.connect(urlServer, null);
//                Channel channel = client.getChannelFuture().channel();
//                ChannelFuture channelFuture = channel.writeAndFlush(buildClientMessage());
//                channelFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
//                    @Override
//                    public void operationComplete(Future<? super Void> future) throws Exception {
//                        Object object = future.get();
//                        System.out.println(object);
//                        //other
//                    }
//                });
            }
        });
        t2.start();
        System.in.read();
    }

    private static NettyMessage buildClientMessage(){
        NettyMessage nettyMessage = new NettyMessage();
        nettyMessage.setHeader(new Header().setCrcCode(1).setLength(3).setType((byte) 1));
        String string = "client";
        nettyMessage.setBody(string);
        return nettyMessage;
    }

    private static NettyMessage buildResponseMessage(){
        NettyMessage nettyMessage = new NettyMessage();
        nettyMessage.setHeader(new Header().setCrcCode(1).setLength(3).setType((byte) 2));
        String string = "server";
        nettyMessage.setBody(string);
        return nettyMessage;
    }
}
