package com.wxf.tomtiger.common;

import java.io.IOException;

/**
 * @author wangxf1
 */
public interface Servlet {
    void get(Request request, Response response) throws IOException;

    void post(Request request, Response response);

}
