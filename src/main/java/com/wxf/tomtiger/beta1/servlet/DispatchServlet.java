package com.wxf.tomtiger.beta1.servlet;

import com.wxf.tomtiger.beta1.common.Request;
import com.wxf.tomtiger.beta1.common.Response;
import com.wxf.tomtiger.beta1.handle.HandleMapping;
import com.wxf.tomtiger.beta1.handle.ResourceHandle;

import org.dom4j.DocumentException;


import java.io.IOException;
import java.io.OutputStream;

/**
 * @author wangxf1
 */
public class DispatchServlet {
    HandleMapping handleMapping = new HandleMapping();

    public void staticResourceProcessor(OutputStream outputStream, Request request) throws IOException {
        ResourceHandle resouceHandle = new ResourceHandle(outputStream);
        resouceHandle.setRequest(request);
        resouceHandle.sendStaticResource();
    }

    public void controllerProcessor(OutputStream outputStream, Request request) throws IOException, DocumentException {
        //创建响应对象
        Response response = new Response(outputStream);
        handleMapping.handleMapping(request, response);


    }
}
