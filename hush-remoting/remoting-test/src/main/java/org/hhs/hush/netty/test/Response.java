package org.hhs.hush.netty.test;

public class Response {
    private String responseName;
    private String responseBody;

    public String getResponseName() {
        return responseName;
    }

    public Response setResponseName(String responseName) {
        this.responseName = responseName;
        return this;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public Response setResponseBody(String responseBody) {
        this.responseBody = responseBody;
        return this;
    }
}
