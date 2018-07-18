package Tool;

import tomTiger.HttpServer;
import tomTiger.Request;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;



public class FileHandle {
    FileInputStream fis = null;
    OutputStream outputStream;
    byte[] bytes = new byte[Key.BUFFER_SIZE];
    public String FileRead(Request request) throws IOException {

        String fileText=null;
        if(request.getUri()==null){
            return fileText ;
        }
        File file = new File(Key.WEB_ROOT,request.getUri());
        if(file.exists()){
            fis = new FileInputStream(file);
            int ch = fis.read(bytes,0,Key.BUFFER_SIZE);
            while(ch != -1){
                /*outputStream.write(bytes, 0, ch);*/
                ch = fis.read(bytes,0,Key.BUFFER_SIZE);
            }
            //已经读到末流尾
            String s = new String(bytes);
            fileText=s;
        }else{
             fileText = "File not found";

        }

        return fileText;

    }
}
