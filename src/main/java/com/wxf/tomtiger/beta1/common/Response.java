package com.wxf.tomtiger.beta1.common;


import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author wangxf1
 */
public class Response {

    private OutputStream outputStream;
    private int statusCode;
    private String statusMessage;
    private Map<String, String> header;

    public Response() {

    }

    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }


    public void sendResponse(String body) throws IOException {

        try {
            //sendHeader
            outputStream.write(String.format("HTTP1.1 %d%s\r\n", statusCode, statusMessage).getBytes());


            for (Map.Entry<String, String> entry : header.entrySet()) {
                outputStream.write(String.format("%s: %s\r\n", entry.getKey(), entry.getValue()).getBytes());
            }
            outputStream.write("\r\n".getBytes());

            //sendBody
            if (!body.isEmpty()) {
                outputStream.write(body.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


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
