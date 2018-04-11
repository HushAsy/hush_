package org.hhs.remoting.api;

import io.netty.channel.Channel;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.Map;

public interface Server {

    Map<String, Channel> getChannels();

    Channel getChannl(InetSocketAddress remoteAddress);
}
