package org.hhs.remoting.netty;

import io.netty.channel.Channel;
import org.hhs.common.rpc.NetUtils;
import org.hhs.common.rpc.URL;
import org.hhs.remoting.api.Client;
import org.hhs.remoting.api.model.Header;
import org.hhs.remoting.api.model.NettyMessage;
import org.hhs.remoting.netty.handler.RequestHandler;

public class NettyServerTest {

    public static void main(String...args){
        NettyTransporter transporter = new NettyTransporter();
        String urlServerTest = "dubbo://admin:hello1234@"+ NetUtils.getLocalAddress()+":20880/context/path?ad=jj";
        URL urlServer = URL.valueOf(urlServerTest);
        Client client = transporter.connect(urlServer, new RequestHandler());
        Channel chanel = client.getChannelFuture().channel();
        chanel.writeAndFlush(buildClientMessage());
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
