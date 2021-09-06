package com.wxf.tomtiger.beta2;

import com.wxf.tomtiger.domain.NioHttpRequest;
import com.wxf.tomtiger.domain.NioHttpResponse;
import com.wxf.tomtiger.common.BizEngine;
import org.dom4j.DocumentException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author: wangxiaofeng
 * @version:
 * @date: 2021/9/5 10:57
 */
public class HttpNioServer {
    private Selector selector;

    BizEngine dispatchServlet = new BizEngine();

    private void init() throws IOException, DocumentException {

        selector = Selector.open();
        ServerSocketChannel channel = ServerSocketChannel.open();

        channel.configureBlocking(false);
        channel.socket().bind(new InetSocketAddress(8080));

        channel.register(this.selector, SelectionKey.OP_ACCEPT);

    }



    public void run() throws DocumentException {
        HttpNioServer server = new HttpNioServer();
        try {
            server.init();
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void start() throws IOException, DocumentException {
        ConcurrentLinkedQueue<NioHttpRequest> reqList = new ConcurrentLinkedQueue<>();
        ConcurrentLinkedQueue<NioHttpResponse> rspList = new ConcurrentLinkedQueue<>();
        while (true) {
            if (selector.select(5000) == 0) {
                System.out.println("[Server]: 目前没有人连接，我做其他事情咯！！");
            }

            Iterator<SelectionKey> iterator = this.selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();

                if (key.isAcceptable()) {
                    accept(key);
                } else if (key.isReadable() && key.isValid()) {
                    reqList.add(read(key));
                    key.interestOps(SelectionKey.OP_WRITE);
                } else if (key.isWritable() && key.isValid()) {
                    rspList.add(write(key));
                    key.interestOps(SelectionKey.OP_READ);
                }
                if (!reqList.isEmpty() && !rspList.isEmpty()) {
                    dispatchServlet.dispatch(reqList.poll(), rspList.poll());
                }


            }
        }
    }

    private NioHttpResponse write(SelectionKey key) {
        NioHttpResponse response = new NioHttpResponse();
        response.setSelectionKey(key);
        return response;
    }

    private NioHttpRequest read(SelectionKey key) throws IOException {
        //创建一个缓冲区
        NioHttpRequest request = new NioHttpRequest();
        request.setSelectionKey(key);
        request.parse();
        return request;
    }

    private void accept(SelectionKey key) throws IOException {
        //事件中传过来的,key我们把这个通道拿到
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel channel = serverSocketChannel.accept();
        //把这个设置为非阻塞
        channel.configureBlocking(false);
        //注册读事件
        channel.register(selector, SelectionKey.OP_READ);

    }
}
