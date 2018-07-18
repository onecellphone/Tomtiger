package Handle;

import Tool.FileHandle;
import Tool.Key;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import tomTiger.Request;
import tomTiger.Response;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Class.forName;

public class HandleMapping {
    private static Map<String,String> getMethod;

    public  void  handleMapping(Request request, Response response) throws DocumentException, IOException {
        /*1.读取配置文件
                2.找出类的名称
                3.通过反射创建类
                4.执行类的方法*/
        loadListen();
         String uri = request.getUri();
         if(uri != null){
         doAction(request,response,uri);
         }



        }
        public static void doAction(Request request,Response response,String methodUrl){
        /*反射调用一般分为3个步骤 1.得到调用类的class,2.得到要调用的类中的方法 3.方法调用*/
        try{
            String target= null;
            target=getMethod.get(methodUrl);
            if(target!=null){
                Class<? extends  Object> cls = Class.forName(target);
                Object obj= cls.newInstance();
                Method[] methods = cls.getDeclaredMethods();
                //因为servlvet里面用了get跟post方法，所以要用数组来存，返回的不能是method对象
                for(Method method :methods){
                    if("get".equals(method.getName())){
                        method.invoke(obj,request,response);//执行
                    }

                }
                response.setStatusCode(200);
                response.setStatusMessage("OK");
                response.setHeader(request.getHeader());
                response.sendResponse("恭喜你打开成功");
            }
            else {
                response.setStatusCode(500);
                response.setStatusMessage("Not Found");
                response.setHeader(request.getHeader());
                response.sendResponse("恭喜你打开失败");
            }





        }
        catch (Exception e ){
            e.printStackTrace();
        }

        }


    private   void loadListen(){
        getMethod=new HashMap<String,String>();
        try{
            SAXReader reader = new SAXReader();

            File file = new File("config/action.xml");
            Document doc =reader.read(file);
            Element root = doc.getRootElement();

            List<Element> list = root.elements("listen");
            for(Element e:list){
                Element action = e.element("action");
                if ("GET".equals(action.attributeValue("method").toUpperCase())){
                    getMethod.put(action.getText(),e.elementText("target"));
                }
        }



    } catch (Exception e) {
            e.printStackTrace();
        }

    }
}