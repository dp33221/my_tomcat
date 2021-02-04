package utils;

import java.io.IOException;

/**
 * @author dingpei
 * @Description: todo
 * @date 2020/12/28 10:24 上午
 */
public class HttpProtocalUtils {


    public static String setHttp200(Long size) throws IOException {
        return "HTTP/1.1 200 OK \n"+
                "Content-Type: text/html \n"+
                "Content-Length:"+size+"\n"+
                "\r\n";

    }

    public static String setHttp404() throws IOException {
        String str="<h1>404 not found</h1>";
        return "HTTP/1.1 404 NOT FOUND \n"+
                "Content-Type: text/html \n"+
                "Content-Length:"+str.getBytes().length+"\n" +
                "\r\n"+str;
    }
}
