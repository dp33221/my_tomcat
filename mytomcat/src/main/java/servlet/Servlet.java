package servlet;

import domain.Request;
import domain.Response;

/**
 * @author dingpei
 * @Description: todo
 * @date 2020/12/28 9:47 下午
 */
public abstract class Servlet {
    protected abstract void doGet(Request request, Response response);
    protected abstract void doPost(Request request, Response response);
    public void service(Request request, Response response){
        if("GET".equals(request.getMethod())){
            doGet(request, response);
        }else{
            doPost(request, response);
        }
    }
}
