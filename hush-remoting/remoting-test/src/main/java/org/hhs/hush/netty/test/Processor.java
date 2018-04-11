package org.hhs.hush.netty.test;

public interface Processor {
    public void handler(Request request, Response response, ProcessChain p);
}
