package com.wxf.tomtiger.beta1.handle;


import com.wxf.tomtiger.beta1.common.Request;
import com.wxf.tomtiger.beta1.util.FileUtil;


import java.io.IOException;
import java.io.OutputStream;

/**
 * @author wangxf1
 */
public class ResourceHandle {
    Request request;
    OutputStream outputStream;
    String result = "File not found";
    String message;

    public ResourceHandle(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void sendStaticResource() throws IOException {

        if (request.getUri() == null) {
            return;
        }
        try {
            String fileContent = FileUtil.fileRead(request);
            if (result.equals(fileContent)) {
                message = "HTTP/1.1 404 Not Found\r\n";

            } else {
                message = "HTTP/1.1 200 OK\r\n";
            }
            message += "\r\n";
            message += fileContent;

            outputStream.write(message.getBytes());
            outputStream.flush();

        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }


    }
}
