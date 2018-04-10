package org.hhs.remoting.api;

import io.netty.channel.ChannelHandler;
import org.hhs.common.rpc.URL;

public interface Transporter {

    Server bind(URL url, ChannelHandler handler);

    Client connect(URL url, ChannelHandler handler);
}
