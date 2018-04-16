package org.hhs.remoting.api;

import org.hhs.remoting.api.model.NettyMessage;

/**
 * @description:
 * @author: hewater
 * @create: 2018-04-16 21:05
 **/
public class RpcFuture {
    //-1表示未完成，1表示已完成
    private volatile int state = -1;

    private NettyMessage nettyMessage;

    public RpcFuture(NettyMessage nettyMessage){
        this.nettyMessage = nettyMessage;
    }

    public NettyMessage getNettyMessage() {
        return nettyMessage;
    }

    private NettyMessage result;

    public void setResult(NettyMessage o){
        state = 1;
        this.result = o;
    }

    public NettyMessage getResult() {
        return result;
    }

    public int getState() {
        return state;
    }

    public boolean isDone(){
        if (getState() == 1){
            return true;
        }else {
            return false;
        }
    }
}
