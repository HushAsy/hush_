package org.hhs.remoting.api;

import java.util.Map;

public class RpcInvocation implements Invocation{

    private String methodName;

    private Class<?>[] parameterTypes;

    private Object[] arguments;

    private Map<String, Object> attachments;

    public String getMethodName() {
        return methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public Map<String, Object> getAttachments() {
        return attachments;
    }

    public Object getAttachment(String key) {
        return attachments.get(key);
    }

    public Object getAttachment(String key, String defaultValue) {
        return attachments.get(key) == null ? defaultValue:attachments.get(key);
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }

    public void setAttachments(Map<String, Object> attachments) {
        this.attachments = attachments;
    }
}
