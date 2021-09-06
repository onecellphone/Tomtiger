package com.wxf.tomtiger.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangxf1
 */
public class Request {
    private String uri;
    private String httpVersion;
    private Map<String, String> header = new HashMap<>();
    private String host;
    private String method;

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


    protected void fillingField(Map<String, Object> paramMap) throws IOException {
        uri = paramMap.get("uri").toString();
        header = (Map<String, String>) paramMap.get("httpHeader");
        httpVersion = paramMap.get("httpVersion").toString();
        method = paramMap.get("method").toString();
    }
}
