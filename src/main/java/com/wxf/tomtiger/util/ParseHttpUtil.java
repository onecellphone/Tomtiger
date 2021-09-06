package com.wxf.tomtiger.util;



import com.sun.xml.internal.ws.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: wangxiaofeng
 * @version:
 * @date: 2021/9/6 14:24
 */
public class ParseHttpUtil {

    public static Map parse(String request){
        Map<String, Object> map = new HashMap(16);
        String uri = parseUri(request);
        String method = parseMethod(request);
        String httpVersion = parseHttpVersion(request);
        Map httpHeader = parseHttpHeader(request);
        if(uri == null || method == null  || httpVersion == null){
            map.put("code", 0);
            return map;
        }
        map.put("uri",uri);
        map.put("method", method);
        map.put("httpVersion", httpVersion);
        map.put("httpHeader", httpHeader);
        map.put("code", 1);
        return map;
    }


    //用于解析http请求的数据
    public static Map parse(InputStream inputStream) throws IOException {
        Map<String, Object> map = new HashMap(16);
        StringBuffer sb = new StringBuffer(2048);
        int i;
        byte[] buffer = new byte[2048];
        try {
            i = inputStream.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            i = -1;
        }
        for (int j = 0; j < i; j++) {
            sb.append((char) buffer[j]);
        }
        System.out.println("request params");
        String request = sb.toString();
        System.out.println(request);


        String uri = parseUri(request);
        String method = parseMethod(request);
        String httpVersion = parseHttpVersion(request);
        Map httpHeader = parseHttpHeader(request);
        if(uri == null || method == null || httpHeader.isEmpty() || httpVersion == null){
            map.put("code", 0);
            return map;
        }
        map.put("uri",uri);
        map.put("method", method);
        map.put("httpVersion", httpVersion);
        map.put("httpHeader", httpHeader);
        map.put("code", 1);
        return map;


    }

    private static String parseMethod(String requestString) {
        int index = requestString.indexOf(" ");
        if (index > 0) {
            return requestString.substring(0, index);
        }
        return null;
    }

    private static String parseHttpVersion(String requestString) {
        int index1 = -1;
        int i = 0;
        do {
            index1 = requestString.indexOf(" ", index1 + 1);
            i++;
        } while (i < 2 && index1 != -1);
        if (i == 2 && index1 != -1) {
            int index2 = requestString.indexOf("\r");
            if (index2 > index1) {
                return requestString.substring(index1 + 1, index2);
            }
        }
        return null;
    }

    private static Map parseHttpHeader(String requestString) {
        Map<String, Object> headerMap = new HashMap(16);
        int index1 = requestString.indexOf("\n");
        int index2;
        int index3;
        int headersEnd = requestString.indexOf("\r\n\r\n");
        if (headersEnd == -1) {
            return new HashMap();
        }
        while (index1 != -1 && index1 < headersEnd) {
            index2 = requestString.indexOf(":", index1 + 1);
            index3 = requestString.indexOf("\n", index1 + 1);
            if (index2 != -1 && index3 > index2) {
                headerMap.put(requestString.substring(index1 + 1, index2).trim(),
                        requestString.substring(index2 + 1, index3).trim());
            }
            index1 = index3;
        }
        return headerMap;
    }

    //方法parseUri()是通过搜索请求的第一个空格和第二个空格间的字符串来得到URI的。
    private static String parseUri(String requestString) {
        int index1, index2;
        index1 = requestString.indexOf(" ");
        if (index1 != -1) {
            index2 = requestString.indexOf(" ", index1 + 1);
            if (index2 > index1) {
                return requestString.substring(index1 + 1, index2);
            }
            return null;
        } else {
            return null;
        }
    }
}
