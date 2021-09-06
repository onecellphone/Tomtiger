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

        String uri = request.getUri();
        if (uri == null) {
            System.out.println("uri is null");
            return;
        }
        invoke(request, response, uri);
    }

    public void invoke(Request request, Response response, String methodUrl) {

        try {
            String target = null;
            //获得执行servlet的class
            target = ServletMappingConfiguration.servletMapping.get(methodUrl);
            if (target != null) {
                Class<? extends Object> cls = Class.forName(target);
                Object obj = cls.newInstance();
                Method[] methods = cls.getDeclaredMethods();
                //因为servlet里面用了get跟post方法，所以要用数组来存，返回的不能是method对象
                for (Method method : methods) {
                    if (request.getMethod().equalsIgnoreCase(method.getName())) {
                        method.invoke(obj, request, response);
                    }
                }

            } else {
                response.setStatusCode(404);
                response.setStatusMessage("Not Found");
                response.generateBody("恭喜你打开失败");
                response.write();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}