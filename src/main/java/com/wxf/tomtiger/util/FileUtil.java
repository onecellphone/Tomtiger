package com.wxf.tomtiger.util;


import com.wxf.tomtiger.common.Request;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


/**
 * @author wangxf1
 */
public class FileUtil {


    public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "web";


    public static String fileRead(Request request) throws IOException {

        int bufferSize = 1024;
        FileInputStream fis = null;

        byte[] bytes = new byte[bufferSize];
        String fileText = null;
        if (request.getUri() == null) {
            return null;
        }
        File file = new File(WEB_ROOT, request.getUri());
        if (file.exists()) {
            fis = new FileInputStream(file);
            int ch = fis.read(bytes, 0, bufferSize);
            while (ch != -1) {
                /*outputStream.write(bytes, 0, ch);*/
                ch = fis.read(bytes, 0, bufferSize);
            }
            //已经读到末流尾
            fileText = new String(bytes);
        } else {
            fileText = "File not found";

        }
        fis.close();

        return fileText;

    }
}
