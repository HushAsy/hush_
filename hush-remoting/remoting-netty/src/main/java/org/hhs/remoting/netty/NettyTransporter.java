package org.hhs.remoting.netty;

import io.netty.channel.ChannelHandler;
import org.hhs.common.rpc.URL;
import org.hhs.remoting.api.Client;
import org.hhs.remoting.api.Server;
import org.hhs.remoting.api.Transporter;

/**
 * @description:
 * @author: hewater
 * @create: 2018-04-10 22:03
 **/
public class NettyTransporter implements Transporter{

    @Override
    public Server bind(URL url, ChannelHandler handler) {
        return new NettyServer(url, handler);
    }

    @Override
    public Client connect(URL url, ChannelHandler handler) {
        return new NettyClient();
    }
}
