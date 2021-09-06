package com.wxf.tomtiger.domain;

import com.wxf.tomtiger.common.Request;
import com.wxf.tomtiger.util.ParseHttpUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author: wangxiaofeng
 * @version:
 * @date: 2021/9/6 14:25
 */
public class BioHttpRequest extends Request {
    private InputStream inputStream;

    public BioHttpRequest(InputStream inputStream) {
        this.inputStream = inputStream;
    }


    public boolean parse() throws IOException {
        Map<String, Object> paramMap = ParseHttpUtil.parse(inputStream);
        int code = (int) paramMap.get("code");
        if(code == 0){
            System.out.println("param error");
            return false;
        }

        super.fillingField(paramMap);
        return true;
    }
}
