package Processor;

import Handle.HandleMapping;
import org.dom4j.DocumentException;
import tomTiger.Request;
import tomTiger.StaticResouceResponse;

import java.io.IOException;
import java.io.OutputStream;

public class ResouceProcessor {
    HandleMapping handleMapping=new HandleMapping();
    public void staicResourceProcesser(OutputStream outputStream, Request request) throws IOException {
        StaticResouceResponse staticResouceResponse=new StaticResouceResponse(outputStream);
        staticResouceResponse.setRequest(request);
        staticResouceResponse.sendStaticResource();

    }
    public void controllerProcesser(OutputStream outputStream,Request request) throws IOException, DocumentException {
        //创建响应对象
        tomTiger.Response response= new tomTiger.Response(outputStream);

        handleMapping.handleMapping(request,response);


    }
}
