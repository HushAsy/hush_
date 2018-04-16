package org.hhs.remoting.netty;

import org.hhs.common.rpc.NetUtils;
import org.hhs.common.rpc.URL;
import org.hhs.remoting.api.Client;
import org.hhs.remoting.api.Server;
import org.hhs.remoting.api.model.Header;
import org.hhs.remoting.api.model.NettyMessage;
import org.hhs.remoting.netty.handler.RpcClientHandler;
import org.junit.Test;

import java.io.IOException;

/**
 * @description:
 * @author: hewater
 * @create: 2018-04-01 23:35
 **/
public class NettyClientTest {

    public static void main(String...args) throws InterruptedException, IOException {
        NettyTransporter transporter = new NettyTransporter();
        String urlServerTest = "dubbo://admin:hello1234@"+ NetUtils.getLocalAddress()+":20880/context/path?ad=jj";
        URL urlServer = URL.valueOf(urlServerTest);
        NettyClient client = new NettyClient(urlServer, null);
        Thread.sleep(1000);
        RpcClientHandler rpcClientHandler = client.getRpcClientHandler();
        rpcClientHandler.sendRequest(buildClientMessage());
        client.getChannelFuture().channel().closeFuture().sync();
    }

    private static NettyMessage buildClientMessage(){
        NettyMessage nettyMessage = new NettyMessage();
        nettyMessage.setHeader(new Header().setCrcCode(1).setLength(3).setType((byte) 1));
        String string = "client";
        nettyMessage.setRequestId("firstTest");
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
