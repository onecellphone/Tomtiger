package Threads;

import Handle.HandleMapping;
import Processor.ResouceProcessor;
import tomTiger.Request;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;

public class ThreadPoolTask implements Runnable,Serializable {
    private final Socket socket;
    InputStream inputStream = null;
    OutputStream outputStream = null;
    private boolean shutdown = false;
    HandleMapping handleMapping=new HandleMapping();
    ResouceProcessor resouceProcessor =new ResouceProcessor();

    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

    public ThreadPoolTask(Socket socket){
      this.socket = socket;
   }

    @Override
    public void run() {
        try{
            System.out.println("线程"+Thread.currentThread().getName()+"在帮我干活");
            //socket.read
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            //创建请求对象并解析
            Request request = new Request(inputStream);

            request.parse();
            if(request.getMethod() == null){
                socket.close();
                return;
            }
          /*  if(request.getUri().startsWith())*/
            Boolean res = request.getUri().contains(".html");
            if(res){
                resouceProcessor.staicResourceProcesser(outputStream,request);
                System.out.println("对的，你就是html");
            }
            else{
               resouceProcessor.controllerProcesser(outputStream,request);
            }


          /*  //创建响应对象
            tomTiger.Response response= new tomTiger.Response(outputStream);

            handleMapping.handleMapping(request,response);*/
          /*  response.setRequest(request);
            response.sendStaticResource();*/


            //关闭socket
            if(socket != null){
                socket.close();
            }

            //检查URI是否是一个关闭命令
            shutdown = SHUTDOWN_COMMAND.equals(request.getUri());

            Thread.sleep(1000);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
