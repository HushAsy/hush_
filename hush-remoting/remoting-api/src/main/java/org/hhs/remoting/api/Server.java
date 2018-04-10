package org.hhs.remoting.api;

import io.netty.channel.Channel;

import java.net.InetSocketAddress;
import java.util.Collection;

public interface Server {

    Collection<Channel> getChannels();

    Channel getChannl(InetSocketAddress remoteAddress);
}
