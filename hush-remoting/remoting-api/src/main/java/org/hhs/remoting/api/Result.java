package org.hhs.remoting.api;

public interface Result {

    //rpc result
    Object getValue();

    Throwable getException();

    boolean hasException();


}
