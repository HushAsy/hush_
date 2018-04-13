package org.hhs.remoting.api.model;

/**
 * Created by 3307 on 2016/3/4.
 */
public enum MessageType {

    RPC_REQUEST((byte) 1),

    RPC_RESPONSE((byte) 2),

    /**
     * 握手请求消息
     */
    LOGIN_REQ((byte)3),
    /**
     * 握手应答消息
     */
    LOGIN_RESP((byte)4),
    /**
     * 心跳请求消息
     */
    HEARTBEAT_REQ((byte)5),
    /**
     * 心跳应答消息
     */
    HEARTBEAT_RESP((byte)6),
    ;

    byte value;

    MessageType(byte i) {
        this.value = i;
    }

    public byte getaByte() {
        return this.value;
    }
}
