package com.wxf.tomtiger.beta1.common;

import java.io.IOException;

/**
 * @author wangxf1
 */
public interface Servlet {
    void get(Request request, Response response) throws IOException;

    void post(Request request, Response response);

}
