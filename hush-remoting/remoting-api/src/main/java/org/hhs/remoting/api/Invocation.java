package org.hhs.remoting.api;

import java.util.Map;

public interface Invocation {

    String getMethodName();

    Class<?>[] getParameterTypes();

    Object[] getArguments();

    Map<String, Object> getAttachments();

    Object getAttachment(String key);

    Object getAttachment(String key, String defaultValue);

}
