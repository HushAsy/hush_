package org.hhs.hush.netty.test;

import io.netty.channel.ChannelPipeline;

public class OneHandler implements Processor {

    @Override
    public void handler(Request request, Response response, ProcessChain p) {
        String req = request.getRequestName();
        request.setRequestName(req+"-->"+this.getClass().getSimpleName());
        p.handler(request, response, p);
        String res = response.getResponseName();
        response.setResponseName(res+"-->"+this.getClass().getSimpleName());
    }
}
