package com.wxf.tomtiger.common;


import java.io.IOException;
import java.util.Map;

/**
 * @author wangxf1
 */
public class Response {

    private int statusCode;
    private String statusMessage;
    private Map<String, String> header;
    public String body;


    public Response() {

    }


    public void generateBody(String body){
        try {
            //sendHeader
            StringBuffer sb = new StringBuffer();
            sb.append(String.format("HTTP1.1 %d %s\r\n", statusCode, statusMessage));
            if(header != null){
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    sb.append(String.format("%s: %s\r\n", entry.getKey(), entry.getValue()));
                }
            }else {
                sb.append(String.format("%s: %s\r\n", "Accept", "application/json"));
            }
            sb.append("\r\n");
            sb.append(body);

            this.body = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void write() throws IOException {

    }


    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }


}
