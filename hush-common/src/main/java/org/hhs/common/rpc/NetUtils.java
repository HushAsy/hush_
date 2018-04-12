package org.hhs.common.rpc;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public class NetUtils {

    public static String getAddressStr(InetSocketAddress socketAddress){
        return socketAddress.getAddress() +":"+ socketAddress.getPort();
    }

    public static String getLocalAddress() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostAddress();
        }catch (UnknownHostException e){
            e.printStackTrace();
        }
        return null;
    }

    public static int defaultPort(){
        return 10000;
    }
}
