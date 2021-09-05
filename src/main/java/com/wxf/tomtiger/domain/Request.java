package com.wxf.tomtiger.domain;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangxf1
 */
public class Request {

    private InputStream inputStream;
    private String uri;
    private String httpVersion;
    private Map<String, String> header = new HashMap<>();
    private String host;
    private String method;


    public Request(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Request() {

    }


    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }


    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }



    public void parse(String request){
        uri = parseUri(request);

        method = this.parseMethod(request);
        System.out.println(method);

        httpVersion = this.pareseHttpVersion(request);
        System.out.println(httpVersion);
        this.parseHttpHeader(request);
    }


    //用于解析http请求的数据
    public void parse() throws IOException {
        StringBuffer request = new StringBuffer(2048);
        int i;
        byte[] buffer = new byte[2048];
        try {
            i = inputStream.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            i = -1;
        }
        for (int j = 0; j < i; j++) {
            request.append((char) buffer[j]);
        }
        System.out.println("request params");
        System.out.println(request.toString());

        uri = parseUri(request.toString());

        String requstStr = request.toString();
        method = this.parseMethod(requstStr);
        System.out.println(method);

        httpVersion = this.pareseHttpVersion(requstStr);
        System.out.println(httpVersion);
        this.parseHttpHeader(requstStr);


    }

    private String parseMethod(String requestString) {
        int index = requestString.indexOf(" ");
        if (index > 0) {
            return requestString.substring(0, index);
        }
        return null;
    }

    private String pareseHttpVersion(String requestString) {
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

    private void parseHttpHeader(String requestString) {
        int index1 = requestString.indexOf("\n");
        int index2;
        int index3;
        int headersEnd = requestString.indexOf("\r\n\r\n");//头结束
        if (headersEnd == -1) {
            return;
        }
        while (index1 != -1 && index1 < headersEnd) {
            index2 = requestString.indexOf(":", index1 + 1);
            index3 = requestString.indexOf("\n", index1 + 1);
            if (index2 != -1 && index3 > index2) {
                this.header.put(requestString.substring(index1 + 1, index2).trim(),
                        requestString.substring(index2 + 1, index3).trim());
            }
            index1 = index3;
        }
    }

    //方法parseUri()是通过搜索请求的第一个空格和第二个空格间的字符串来得到URI的。
    private String parseUri(String requestString) {
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

    public String getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }


}
