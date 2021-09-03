package com.wxf.tomtiger.beta1;

import com.wxf.tomtiger.beta1.task.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HttpServer {


    //收到关闭命令
    private boolean shutDown = false;

    /**
     * @param args
     */
    public static void main(String[] args) {
        HttpServer server = new HttpServer();
        server.await();

    }

    public void await() {
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

                // TODO: 实现一个线程，处理本客户端的请求与响应
                // TODO: 实现线程池
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
