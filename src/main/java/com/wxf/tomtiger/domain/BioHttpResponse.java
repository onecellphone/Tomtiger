package com.wxf.tomtiger.domain;

import com.wxf.tomtiger.common.Response;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author: wangxiaofeng
 * @version:
 * @date: 2021/9/6 14:25
 */
public class BioHttpResponse extends Response {

    private OutputStream outputStream;

    public BioHttpResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void write() throws IOException {
        if(body == null){
            return;
        }
        outputStream.write(body.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
    }
}
