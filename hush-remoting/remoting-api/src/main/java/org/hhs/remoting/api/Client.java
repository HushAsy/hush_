package org.hhs.remoting.api;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

public interface Client  {

    void reconnect();

    ChannelFuture getChannelFuture();

}
