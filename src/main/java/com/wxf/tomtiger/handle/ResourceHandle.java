package com.wxf.tomtiger.handle;


import com.wxf.tomtiger.common.Request;
import com.wxf.tomtiger.common.Response;
import com.wxf.tomtiger.util.FileUtil;


/**
 * @author wangxf1
 */
public class ResourceHandle {
    Request request;
    Response response;

    public ResourceHandle() {

    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void resolveView() {
        String result = "File not found";
        try {
            String fileContent = FileUtil.fileRead(request);
            if (result.equals(fileContent)) {
                response.setStatusCode(200);

            } else {
               response.setStatusCode(404);
            }
            response.generateBody(fileContent);
            response.write();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
