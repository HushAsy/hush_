package org.hhs.remoting.api;

import java.io.Serializable;

public class Response implements Serializable{
    private static final long serialVersionUID = 1L;
    private String name;
    private String body;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Response{" +
                "name='" + name + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
