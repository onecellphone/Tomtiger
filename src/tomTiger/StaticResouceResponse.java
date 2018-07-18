package tomTiger;

import Tool.FileHandle;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class StaticResouceResponse {
    Request request;
    OutputStream outputStream;
    String result="File not found";
    String Message;
    FileHandle fileHandle =new FileHandle();

    public StaticResouceResponse(OutputStream outputStream) {
        this.outputStream =outputStream;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void sendStaticResource() throws IOException {
        FileInputStream fis = null;
        if(request.getUri()==null){
            return;
        }
        try{
            if(result.equals(fileHandle.FileRead(request).toString())){
                Message = "HTTP/1.1 404 Not Found\r\n";
                Message += "\r\n";
                Message += fileHandle.FileRead(request);

            }
            else {
                Message = "HTTP/1.1 200 OK\r\n";
                Message += "\r\n";
                Message += fileHandle.FileRead(request);
            }

            outputStream.write(Message.getBytes());
            outputStream.flush();

        }catch(Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
        }finally{
            if(fis != null) {
                fis.close();
            }
        }


    }
}
