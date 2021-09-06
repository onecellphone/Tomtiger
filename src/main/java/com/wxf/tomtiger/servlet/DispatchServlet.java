package com.wxf.tomtiger.servlet;

import com.wxf.tomtiger.common.Request;
import com.wxf.tomtiger.common.Response;
import com.wxf.tomtiger.handle.HandleMapping;
import com.wxf.tomtiger.handle.ResourceHandle;

import org.dom4j.DocumentException;


import java.io.IOException;


/**
 * @author wangxf1
 */
public class DispatchServlet {


    public void staticResourceProcessor(Response response, Request request) throws IOException {
        ResourceHandle resourceHandle = new ResourceHandle();
        resourceHandle.setRequest(request);
        resourceHandle.setResponse(response);
        resourceHandle.sendStaticResource();
    }

    public void servletProcessor(Response response, Request request) throws IOException, DocumentException {
        HandleMapping handleMapping = new HandleMapping();
        handleMapping.invoke(request, response);


    }

    public void dispatch(Request request, Response response) throws IOException, DocumentException {
        boolean res = request.getUri().contains(".html");
        if (res) {
            staticResourceProcessor(response, request);
        } else {
            servletProcessor(response, request);
        }
    }
}
