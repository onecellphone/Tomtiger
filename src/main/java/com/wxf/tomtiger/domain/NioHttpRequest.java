package com.wxf.tomtiger.domain;

import com.wxf.tomtiger.domain.Request;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * @author: wangxiaofeng
 * @version:
 * @date: 2021/9/5 13:48
 */
public class NioHttpRequest extends Request {
    private SelectionKey selectionKey;

    public SelectionKey getSelectionKey() {
        return selectionKey;
    }

    public void setSelectionKey(SelectionKey selectionKey) {
        this.selectionKey = selectionKey;
    }


    @Override
    public void parse() throws IOException {
        //创建一个缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        SocketChannel channel = (SocketChannel) selectionKey.channel();
        //我们把通道的数据填入缓冲区
        channel.read(buffer);
        String request = new String(buffer.array()).trim();
        System.out.println("客户端的请求内容" + request);
        super.parse(request);

    }



}
