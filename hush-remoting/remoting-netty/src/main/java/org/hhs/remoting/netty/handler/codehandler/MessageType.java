package org.hhs.remoting.netty.handler.codehandler;

public enum MessageType {
    LOGIN_REQ((byte) 1), LOGIN_RESP((byte) 2), HEARTBEAT_REQ((byte) 3), HEARTBEAT_RESP((byte) 4);

    private byte aByte;

    private MessageType(byte aByte){
        this.aByte = aByte;
    }

    public void setaByte(byte aByte){
        this.aByte = aByte;
    }

    public byte getaByte(){
        return aByte;
    }
}
