package com.wxf.tomtiger.domain;

import com.wxf.tomtiger.domain.Response;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author: wangxiaofeng
 * @version:
 * @date: 2021/9/5 14:07
 */
public class NioHttpResponse extends Response {
    private SelectionKey selectionKey;

    public void setSelectionKey(SelectionKey selectionKey) {
        this.selectionKey = selectionKey;
    }

    @Override
    public void write() throws IOException {
        if(this.body == null){
            return;
        }
        System.out.println("response info" + this.body);
        SocketChannel channel = (SocketChannel) selectionKey.channel();
        ByteBuffer bb = ByteBuffer.wrap(body.getBytes(StandardCharsets.UTF_8));
        //  向通道中写入数据
        long len = channel.write(bb);
        if (len == -1) {
            selectionKey.cancel();
        }
        channel.close();

    }
}
