package tomTiger;

import Threads.ThreadPoolTask;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HttpServer {
    /**
     * WEB_ROOT是存放HTML文件的目录
     */


    //关闭命令
    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
    public static  int produceTaskSleepTime=5;
    public static int consumeTaskSleepTime=5000;
    private static  int produceTaskMaxNumber= 20;
    //收到关闭命令
    private boolean shutdown = false;

    /**
     * @param args
     */
    public static void main(String[] args) {
        HttpServer server = new HttpServer();

        server.await();

    }

    public void await() {
        ServerSocket serverSocket = null;
        int port = 8080;//端口
        try{
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("localhost"));
        }catch(IOException e){
            e.printStackTrace();
            System.exit(1);
        }
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 4, 3,
                TimeUnit. SECONDS, new ArrayBlockingQueue<Runnable>(3),
                new ThreadPoolExecutor.DiscardOldestPolicy());
        //等待请求
        while(!shutdown){
            Socket socket = null;
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try{

                socket = serverSocket.accept();

                // TODO: 实现一个线程，处理本客户端的请求与响应
                // TODO: 实现线程池
                    try{
                        //一个任务把他加入到线程池中
                        threadPool.execute(new ThreadPoolTask(socket));
                    }
                    catch (Exception e ){
                        e.printStackTrace();
                    }


            }catch(Exception e){
                e.printStackTrace();
                continue;
            }
        }
    }

}
