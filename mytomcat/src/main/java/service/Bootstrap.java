package service;

import container.MyContext;
import container.MyHost;
import container.MyWrapper;
import domain.Request;
import domain.Response;
import org.dom4j.DocumentException;
import servlet.Servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author dingpei
 * @Description: todo
 * @date 2020/12/28 10:21 上午
 */
public class Bootstrap {
    private Integer port = 8080;

    private Request request;

    public Integer getPort() {
        return port;
    }

    private MyHost myHost;

    private void start() throws IOException {
        ServerSocket serverSocket=new ServerSocket(port);
        System.out.println("mycat启动");
        while(true){
            Socket socket = serverSocket.accept();
            OutputStream outputStream = socket.getOutputStream();
            Request request=new Request(socket);
            final Response response = new Response(outputStream);
            request.getRequest();
            final String[] urlArr = request.getUrl().split("/");
            if(urlArr.length==0){
                response.outputError();
                socket.close();
                continue;
            }
            //项目名
            final String projectName = urlArr[1];
            //请求路径-对应servlet
            String servletUrl="";
            if(urlArr.length>2){
                servletUrl=urlArr[2];
            }
            //根据ip定位host
            final MyContext myContext = myHost.getContextMap().get(request.getIp());
            if(myContext==null){
                response.outputError();
                socket.close();
                continue;
            }
            //根据项目名定位context
            final MyWrapper myWrapper = myContext.getWrapperMap().get(projectName);
            if(myWrapper==null){
                response.outputError();
                socket.close();
                continue;
            }
            //根据url定位servlet
            final Servlet servlet = myWrapper.getServletMap().get("/"+servletUrl);
            if(servlet==null){
                response.outputError();
                socket.close();
                continue;
            }
            servlet.service(request,response);
            socket.close();
        }

    }


    private void init() throws DocumentException, IllegalAccessException, InstantiationException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException {
        //1.初始化host、加载server.xml配置目录下文件
        //2.对每个项目初始化Context 项目名-wrapper
        //3.初始化wapper，加载项目 配置url-servlet
        MyHost myHost=new MyHost();
        myHost.init();
        this.myHost=myHost;

    }

    public static void main(String[] args) {
        Bootstrap bootstrap=new Bootstrap();
        try {
            //初始化
            bootstrap.init();
            //启动
            bootstrap.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
