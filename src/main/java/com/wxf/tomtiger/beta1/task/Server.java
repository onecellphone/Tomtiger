package com.wxf.tomtiger.beta1.task;


import com.wxf.tomtiger.domain.BioHttpRequest;
import com.wxf.tomtiger.domain.BioHttpResponse;
import com.wxf.tomtiger.common.BizEngine;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;

/**
 * 接收请求的server
 *
 * @author wangxf1
 */
public class Server implements Runnable, Serializable {
    private final Socket socket;
    InputStream inputStream = null;
    OutputStream outputStream = null;
    BizEngine bizEngine = new BizEngine();

    public Server(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            //socket.read
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            //创建请求对象并解析
            BioHttpRequest request = new BioHttpRequest(inputStream);

            boolean parseFlag = request.parse();
            if(!parseFlag){
                socket.close();
                return;
            }

            BioHttpResponse response = new BioHttpResponse(outputStream);
            bizEngine.dispatch(request, response);

            socket.close();
            Thread.sleep(1000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
