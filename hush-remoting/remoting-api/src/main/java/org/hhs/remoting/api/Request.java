package org.hhs.remoting.api;

import org.hhs.remoting.api.model.Header;
import org.hhs.remoting.api.model.NettyMessage;

import java.io.Serializable;

public class Request implements Serializable {
    private static final long serialVersionUID = 1L;

    private Invocation invocation;

    public Request(Invocation invocation){
        this.invocation = invocation;
    }

    public NettyMessage getNettyMessage() {
        NettyMessage nettyMessage = new NettyMessage();
        Header header = new Header();
        header.setSessionID(1234567890);
        header.setType((byte) 1);
        nettyMessage.setHeader(header);
        nettyMessage.setBody(invocation);
        return nettyMessage;
    }

}
