package com.wxf.tomtiger.beta1.task;


import com.wxf.tomtiger.domain.BioHttpRequest;
import com.wxf.tomtiger.domain.BioHttpResponse;
import com.wxf.tomtiger.servlet.DispatchServlet;

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
    DispatchServlet dispatchServlet = new DispatchServlet();

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
            request.parse();
            BioHttpResponse response = new BioHttpResponse(outputStream);


            if (request.getMethod() == null) {
                socket.close();
                return;
            }

            dispatchServlet.dispatch(request, response);

            //关闭socket
            socket.close();

            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
