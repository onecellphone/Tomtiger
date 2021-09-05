package com.wxf.tomtiger.beta1;

import com.wxf.tomtiger.config.ServletMappingConfiguration;
import com.wxf.tomtiger.beta1.task.Server;
import org.dom4j.DocumentException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HttpBioServer {


    //收到关闭命令
    private static boolean shutDown = false;


    /**
     * @param args
     */
    public static void main(String[] args) throws DocumentException {
        initServletMapping();
        socketAwait();
    }

    /**
     * init
     */
    private static void initServletMapping() throws DocumentException {
        System.out.println("init servlet mapping");
        ServletMappingConfiguration.initServletMapping();
        System.out.println("output servlet mapping");
        ServletMappingConfiguration.outputServletMapping();

    }

    public static void socketAwait() {
        ServerSocket serverSocket = null;
        int port = 8080;
        try {
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("localhost"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 4, 3,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3),
                new ThreadPoolExecutor.DiscardOldestPolicy());
        //等待请求
        while (!shutDown) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                try {
                    //一个任务把他加入到线程池中
                    threadPool.execute(new Server(socket));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
