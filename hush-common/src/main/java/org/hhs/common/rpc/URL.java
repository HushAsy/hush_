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
    public static URL valueOf(String url){
        String protocol = null;
        String username = null;
        String password = null;
        String host = null;
        int port = 0;
        String path = null;
        Map<String, String> parameters = null;

        URL _url = new URL();
        String urlTest = "dubbo://admin:hello1234@10.20.130.230:20880/context/path?application=morgan&k1=v1&k2=v2";
        if (url == null || url.trim().length() == 0){
            throw new IllegalArgumentException("url == null");
        }
        String[] strings = urlTest.split("\\?");
        Map<String, String> paramsMap = strParse(strings[1]);
        urlTest = strings[0];

        String[] proto = urlTest.split("://");
        protocol = proto[0];

        String userStr = "admin:hello1234@10.20.130.230:20880/context/path";
        String[] userArrs = userStr.split("@");
        username = userArrs[0];
        password = userArrs[1];






        return null;
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

    private static String matchStr(String pat, String str){
        Pattern pattern = Pattern.compile(pat);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()){
            System.out.println(matcher.group());
        }
        return null;
    }

    public static void main(String...args){
    }



}
