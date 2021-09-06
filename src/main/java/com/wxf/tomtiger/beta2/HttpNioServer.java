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

    /**
     * 通道管理器(Selector)
     */
    private Selector selector;

    BizEngine bizEngine = new BizEngine();

    public void run() throws DocumentException {
        HttpNioServer server = new HttpNioServer();
        try {
            server.init();
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() throws IOException, DocumentException {
        // 创建通道ServerSocketChannel
        ServerSocketChannel channel = ServerSocketChannel.open();
        // 将通道设置为非阻塞
        channel.configureBlocking(false);
        // 绑定到指定的端口上
        channel.socket().bind(new InetSocketAddress(8080));
        // 通道管理器(Selector)
        selector = Selector.open();
        /**
         * 将通道(Channel)注册到通道管理器(Selector)，并为该通道注册selectionKey.OP_ACCEPT事件
         * 注册该事件后，当事件到达的时候，selector.select()会返回，
         * 如果事件没有到达selector.select()会一直阻塞。
         */
        channel.register(this.selector, SelectionKey.OP_ACCEPT);

    }

    private void start() throws IOException, DocumentException {
        ConcurrentLinkedQueue<NioHttpRequest> reqList = new ConcurrentLinkedQueue<>();
        ConcurrentLinkedQueue<NioHttpResponse> rspList = new ConcurrentLinkedQueue<>();
        while (true) {
            /**
             * 当注册事件到达时，方法返回，否则该方法会一直阻塞
             * 该Selector的select()方法将会返回大于0的整数，该整数值就表示该Selector上有多少个Channel具有可用的IO操作
             */
            int count = selector.select(5000);
            if ( count == 0) {
                System.out.println("[Server]: 目前没有人连接，我做其他事情咯！！");
            }
            System.out.println("当前有 " + count + " 个channel可以操作");
            // 一个SelectionKey对应一个就绪的通道
            Iterator<SelectionKey> iterator = this.selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                // 客户端请求连接事件，接受客户端连接就绪
                if (key.isAcceptable()) {
                    accept(key);
                } else if (key.isReadable() && key.isValid()) {
                    NioHttpRequest httpRequest = read(key);
                    if(httpRequest == null){
                        continue;
                    }
                    reqList.add(httpRequest);
                    key.interestOps(SelectionKey.OP_WRITE);
                } else if (key.isWritable() && key.isValid()) {
                    rspList.add(write(key));
                    key.interestOps(SelectionKey.OP_READ);
                }
                if (!reqList.isEmpty() && !rspList.isEmpty()) {
                    bizEngine.dispatch(reqList.poll(), rspList.poll());
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
        boolean parseFlag = request.parse();
        if(!parseFlag){
            return null;
        }
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
