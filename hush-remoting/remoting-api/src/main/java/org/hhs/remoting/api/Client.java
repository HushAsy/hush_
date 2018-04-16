package org.hhs.remoting.api;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;

public interface Client  {

    ChannelFuture getChannelFuture();

    ChannelHandler getRpcClientHandler();

}
