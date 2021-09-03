package com.wxf.tomtiger.beta1.servlet;


import com.wxf.tomtiger.beta1.common.Request;
import com.wxf.tomtiger.beta1.common.Response;
import com.wxf.tomtiger.beta1.common.Servlet;

import java.io.IOException;
import java.util.Map;

/**
 * @author wangxf1
 */
public class LoginServlet implements Servlet {
    @Override
    public void get(Request request, Response response) throws IOException {
        //setting utf-8
        Map<String, String> header = request.getHeader();
        header.put("content-type", "text/html;charset=UTF-8");

        response.setHeader(request.getHeader());
        response.setStatusCode(200);
        response.setStatusMessage("OK");
        response.sendResponse("恭喜你成功进入了我们的核心秘密");
    }

    @Override
    public void post(Request request, Response response) {

    }
}
