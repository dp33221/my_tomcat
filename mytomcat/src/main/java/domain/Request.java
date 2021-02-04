package domain;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author dingpei
 * @Description: todo
 * @date 2020/12/28 11:25 上午
 */
public class Request {
    private Socket socket;
    /*
    操作类型
     */
    private String method;
    /*
    请求路径
     */
    private String url;

    private String ip;

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setUrl(String url) {
        this.url = url;
    }



    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Request(Socket socket) {
        this.socket = socket;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void getRequest() throws IOException {
        //获取输入流
        InputStream inputStream = socket.getInputStream();
        int available = 0;
        while(available==0){
            available = inputStream.available();
        }
        byte[] bytes=new byte[available];
        //读取到字节数组
        inputStream.read(bytes);
        //解析请求
        final String req = new String(bytes);
        System.out.println(req);
        String[] split = req.split("\\n");
        String[] firstLine=split[0].split(" ");
        this.method=firstLine[0];
        this.url=firstLine[1];
        this.ip=split[1].split(":")[1].replace(" ","");

    }

}
