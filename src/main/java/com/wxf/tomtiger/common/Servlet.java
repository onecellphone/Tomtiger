package com.wxf.tomtiger.common;

import com.wxf.tomtiger.domain.Request;
import com.wxf.tomtiger.domain.Response;

import java.io.IOException;

/**
 * @author wangxf1
 */
public interface Servlet {
    void get(Request request, Response response) throws IOException;

    void post(Request request, Response response);

}
