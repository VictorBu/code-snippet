package com.karonda.nettywebsocketserver.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class UriUtil {

    public static String getParam(String uri, String paramName) {
        Map<String, String> paramMap = getParamMap(uri);

        if(paramMap.containsKey(paramName)){
            return paramMap.get(paramName);
        }
        return null;
    }

    public static Map<String, String> getParamMap(String uri) {
        Map<String, String> paramMap = new HashMap<>();

        uri = uri.substring(uri.indexOf("?") + 1);
        String[] params = uri.split(Pattern.quote("&"));

        for (String param : params) {
            String[] chunks = param.split(Pattern.quote("="));
            String name = chunks[0], value = null;
            if(chunks.length > 1) {
                value = chunks[1];
            }
            paramMap.put(name, value);
        }
        return paramMap;
    }
}
