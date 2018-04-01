package org.hhs.remoting.netty.handler.codehandler;

import java.util.HashMap;
import java.util.Map;

public class NettyMessage {
    private Header header;
    private Object body;

    /**
     * 消息头
     * @author hewater
     */
    public final class Header {
        private int crcCode = 0xabef0101;
        private int length;
        private long sessionId;
        private byte type;
        private byte priority;
        private Map<String, Object> attachment = new HashMap();

        public int getCrcCode() {
            return crcCode;
        }

        public void setCrcCode(int crcCode) {
            this.crcCode = crcCode;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public long getSessionId() {
            return sessionId;
        }

        public void setSessionId(long sessionId) {
            this.sessionId = sessionId;
        }

        public byte getType() {
            return type;
        }

        public void setType(byte type) {
            this.type = type;
        }

        public Map<String, Object> getAttachment() {
            return attachment;
        }

        public void setAttachment(Map<String, Object> attachment) {
            this.attachment = attachment;
        }

        public byte getPriority() {
            return priority;
        }

        public void setPriority(byte priority) {
            this.priority = priority;
        }

        @Override
        public String toString() {
            return "Header{" +
                    "crcCode=" + crcCode +
                    ", length=" + length +
                    ", sessionId=" + sessionId +
                    ", type=" + type +
                    ", priority=" + priority +
                    ", attachment=" + attachment +
                    '}';
        }
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
