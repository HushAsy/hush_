package org.hhs.remoting.api;

import org.hhs.common.rpc.URL;

public class RpcInvoker implements Invoker {

    private URL _url;

    private Invocation invocation;

    public RpcInvoker(URL url, Invocation invocation){
        this._url = url;
        this.invocation = invocation;
    }

    public URL getUrl() {
        return _url;
    }

    public Invocation getInvocation() {
        return invocation;
    }
}
