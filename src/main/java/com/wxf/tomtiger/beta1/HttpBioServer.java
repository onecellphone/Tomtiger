package com.wxf.tomtiger.beta1;

import com.wxf.tomtiger.beta1.task.Server;
import com.wxf.tomtiger.constants.Constants;
import org.dom4j.DocumentException;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author wangxf1
 */
public class HttpBioServer {


    //收到关闭命令
    private static boolean shutDown = false;


    public void run() throws DocumentException {
        socketAwait();
    }


    private void socketAwait() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(Constants.PORT, 1, InetAddress.getByName(Constants.HOST_NAME));
            ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 4, 3,
                    TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3),
                    new ThreadPoolExecutor.DiscardOldestPolicy());
            //等待请求
            while (!shutDown) {
                Socket socket = null;
                socket = serverSocket.accept();
                threadPool.execute(new Server(socket));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
