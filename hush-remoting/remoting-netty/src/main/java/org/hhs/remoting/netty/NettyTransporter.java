package org.hhs.remoting.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.DefaultChannelPromise;
import org.hhs.common.rpc.URL;
import org.hhs.remoting.api.Client;
import org.hhs.remoting.api.Server;
import org.hhs.remoting.api.Transporter;

import java.net.InetSocketAddress;
import java.util.Map;

/**
 * @description:
 * @author: hewater
 * @create: 2018-04-10 22:03
 **/
public class NettyTransporter implements Transporter{

    @Override
    public Server bind(URL url, ChannelHandler handler) {
        Server server = new Server() {
            @Override
            public Map<String, Channel> getChannels() {
                return null;
            }

            @Override
            public Channel getChannl(InetSocketAddress remoteAddress) {
                return null;
            }
        };
//        server.getChannl().
        DefaultChannelPromise defaultChannelPromise;
        return new NettyServer(url, handler);
    }

    @Override
    public Client connect(URL url, ChannelHandler handler) {
        return new NettyClient(url, handler);
    }
}
