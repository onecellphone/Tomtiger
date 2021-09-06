package com.wxf.tomtiger.handle;


import com.wxf.tomtiger.config.ServletMappingConfiguration;
import com.wxf.tomtiger.common.Request;
import com.wxf.tomtiger.common.Response;
import org.dom4j.DocumentException;

import java.io.IOException;
import java.lang.reflect.Method;


/**
 * @author wangxf1
 */
public class HandleMapping {

    public void invoke(Request request, Response response) throws DocumentException, IOException {
        invoke(request, response, request.getUri());
    }

    public void invoke(Request request, Response response, String url) {

        try {
            String target = null;
            //获得执行servlet的class
            target = ServletMappingConfiguration.servletMapping.get(url);
            if (target != null) {
                Class<? extends Object> cls = Class.forName(target);
                Object obj = cls.newInstance();
                Method[] methods = cls.getDeclaredMethods();

                for (Method method : methods) {
                    if (request.getMethod().equalsIgnoreCase(method.getName())) {
                        method.invoke(obj, request, response);
                    }
                }
            } else {
                returnNotFound(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void returnNotFound(Response response) throws IOException {
        response.setStatusCode(404);
        response.setStatusMessage("Not Found");
        response.generateBody("open error");
        response.write();
    }


}