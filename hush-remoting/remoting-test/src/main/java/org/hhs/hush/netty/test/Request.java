package org.hhs.hush.netty.test;

public class Request {
    private String requestName;
    private String requestBody;

    public String getRequestName() {
        return requestName;
    }

    public Request setRequestName(String requestName) {
        this.requestName = requestName;
        return this;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public Request setRequestBody(String requestBody) {
        this.requestBody = requestBody;
        return this;
    }
}
