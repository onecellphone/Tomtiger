package com.wxf.tomtiger;

import com.wxf.tomtiger.beta1.HttpBioServer;
import com.wxf.tomtiger.beta2.HttpNioServer;
import com.wxf.tomtiger.constants.Constants;
import org.dom4j.DocumentException;

/**
 * @author: wangxiaofeng
 * @version:
 * @date: 2021/9/6 16:08
 */
public class HttpServerApplication {
    /**
     * @param args
     */
    public static void main(String[] args) throws DocumentException {
        String serverType = args[0];
        if(Constants.BIO_SERVER.equalsIgnoreCase(serverType)){
            HttpBioServer httpBioServer = new HttpBioServer();
            httpBioServer.run();
        }
        if(Constants.NIO_SERVER.equalsIgnoreCase(serverType)){
            HttpNioServer httpNioServer = new HttpNioServer();
            httpNioServer.run();
        }
    }
}
