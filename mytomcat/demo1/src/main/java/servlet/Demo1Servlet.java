package servlet;

import domain.Request;
import domain.Response;
import utils.HttpProtocalUtils;

import java.io.IOException;

/**
 * @author dingpei
 * @Description: todo
 * @date 2020/12/29 1:21 下午
 */
public class Demo1Servlet extends Servlet {
    @Override
    protected void doGet(Request request, Response response) {
        doPost(request,response);
    }

    @Override
    protected void doPost(Request request, Response response) {
        try {
            String demo="demo1Servlet------1";
            response.output(HttpProtocalUtils.setHttp200((long) demo.length())+demo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

    }
}
