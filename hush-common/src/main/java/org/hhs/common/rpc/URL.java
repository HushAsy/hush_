package org.hhs.common.rpc;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URL implements Serializable{

    private static final long serialVersionUID = -1985165475234910535L;

    private final String protocol;

    private final String username;

    private final String password;

    private final String host;

    private final int port;

    private final String path;

    private final Map<String, String> parameters;

    protected URL() {
        this.protocol = null;
        this.username = null;
        this.password = null;
        this.host = null;
        this.port = 0;
        this.path = null;
        this.parameters = null;
    }

    public URL(String protocol, String username, String password, String host, int port, String path, Map parameters){
        this.protocol = protocol;
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
        this.path = path;
        this.parameters = parameters;
    }

    //dubbo://admin:hello1234@10.20.130.230:20880/context/path?application=morgan&k1=v1&k2=v2
    public static URL valueOf1(String url){
        String protocol = null;
        String username = null;
        String password = null;
        String host = null;
        int port = 0;
        String path = null;
        Map<String, String> parameters = null;

        URL _url = new URL();
//        String urlTest = "dubbo://admin:hello1234@10.20.130.230:20880/context/path?application=morgan&k1=v1&k2=v2";
        if (url == null || url.trim().length() == 0){
            throw new IllegalArgumentException("url == null");
        }
        String[] strings = url.split("\\?");
        if (strings.length > 1){
            parameters = strParse(strings[1]);
        }
        url = strings[0];
        String[] proto = url.split("://");
        protocol = proto[0];
        String[] userArrs = proto[1].split("@");
        username = userArrs[0].split(":")[0];
        password = userArrs[0].split(":")[1];
        String pa = userArrs[1].substring(userArrs[1].indexOf("/"),userArrs[1].length());
        path = pa;
        String hostP = userArrs[1].substring(0, userArrs[1].indexOf("/"));
        host = hostP.split(":")[0];
        port = Integer.parseInt(hostP.split(":")[1]);
        return new URL(protocol, username, password, host, port, path, parameters);
    }

    public static URL valueOf(String url) {
        if (url == null || (url = url.trim()).length() == 0){
            throw new IllegalArgumentException("url = null");
        }
        String urlTest = "dubbo://admin:hello1234@10.20.130.230:20880/context/path?ad=jj";
        String protocol = null;
        String username = null;
        String password = null;
        String host = null;
        int port = 0;
        String path = null;

        //参数转化
        Map<String, String> parameters = null;
        int i = url.indexOf("?");
        if (i >= 0){
            String params = url.substring(i+1);
            parameters = strParse(params);
            url = url.substring(0, i);
        }
        //url = dubbo://admin:hello1234@10.20.130.230:20880/context/path;
        i = url.indexOf("://");
        if (i >= 0){
            if (i == 0) throw new IllegalStateException("url miss protocal"+url);
            protocol = url.substring(0, i);
            url = url.substring(i + 3);
        } else {
            i = url.indexOf(":/");
            if (i >= 0) {
                if (i == 0) throw new IllegalStateException("url missing protocol: \"" + url + "\"");
                protocol = url.substring(0, i);
                url = url.substring(i + 1);
            }
        }

        //admin:hello1234@10.20.130.230:20880/context/path
        i = url.indexOf("/");
        if (i >= 0){
            path = url.substring(i + 1);
            url = url.substring(0, i);
        }
        //admin:hello1234@10.20.130.230:20880
        i = url.indexOf("@");
        if (i >= 0){
            username = url.substring(0, i);
            int j = username.indexOf(":");
            if (j >= 0){
                password = username.substring(j + 1);
                username = username.substring(0, j);
            }
            url = url.substring(i + 1);
        }
        i = url.indexOf(":");
        if (i >= 0 && i < url.length() - 1){
            port = Integer.parseInt(url.substring(i + 1));
            url = url.substring(0, i);
        }
        if(url.length() > 0){
            host = url;
        }
        return new URL(protocol, username, password, host, port, path, parameters);
    }

    /**
     * 处理字符串，转化为键值对形式:
     * application=morgan&k1=v1&k2=v2
     * @return
     */
    private static Map<String, String> strParse(String str){
        String params = str;
        Map<String, String> paramsMap = null;
        if (params != null && params.trim().length() != 0){
            String[] pars = params.split("&");
            paramsMap = new HashMap<String, String>();
            for (String par : pars){
                String[] temp = par.split("=");
                paramsMap.put(temp[0], temp[1]);
            }
        }
        return paramsMap;
    }

    public static void main(String...args){
        String urlTest = "dubbo://admin:hello1234@10.20.130.230:20880/context/path";
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        return "URL{" +
                "protocol='" + protocol + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", path='" + path + '\'' +
                ", parameters=" + parameters +
                '}';
    }
}
