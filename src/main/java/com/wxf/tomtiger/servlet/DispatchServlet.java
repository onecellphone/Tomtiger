package com.wxf.tomtiger.servlet;

import com.wxf.tomtiger.domain.Request;
import com.wxf.tomtiger.domain.Response;
import com.wxf.tomtiger.handle.HandleMapping;
import com.wxf.tomtiger.handle.ResourceHandle;

import org.dom4j.DocumentException;


import java.io.IOException;


/**
 * @author wangxf1
 */
public class DispatchServlet {


    public void staticResourceProcessor(Response response, Request request) throws IOException {
        ResourceHandle resouceHandle = new ResourceHandle();
        resouceHandle.setRequest(request);
        resouceHandle.setResponse(response);
        resouceHandle.sendStaticResource();
    }

    public void controllerProcessor(Response response, Request request) throws IOException, DocumentException {
        HandleMapping handleMapping = new HandleMapping();
        handleMapping.handleMapping(request, response);


    }

    public void dispatch(Request request, Response response) throws IOException, DocumentException {
        boolean res = request.getUri().contains(".html");
        if (res) {
            staticResourceProcessor(response, request);
        } else {
            controllerProcessor(response, request);
        }
    }
}
