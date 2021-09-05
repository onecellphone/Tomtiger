package com.wxf.tomtiger.servlet;


import com.wxf.tomtiger.domain.Request;
import com.wxf.tomtiger.domain.Response;
import com.wxf.tomtiger.common.Servlet;

import java.io.IOException;


/**
 * @author wangxf1
 */
public class LoginServlet implements Servlet {
    @Override
    public void get(Request request, Response response) throws IOException {

        response.setHeader(request.getHeader());
        response.setStatusCode(200);
        response.setStatusMessage("OK");
        response.generateBody("say hello world");
        response.write();
    }

    @Override
    public void post(Request request, Response response) {

    }
}
