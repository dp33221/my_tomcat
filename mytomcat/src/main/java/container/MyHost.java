package container;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dingpei
 * @Description: todo
 * @date 2020/12/28 10:10 下午
 */
public class MyHost {
    /**
     * 项目目录 对应context
     */
    private Map<String,MyContext> contextMap =new HashMap<String,MyContext>();
    public void init() throws DocumentException, IllegalAccessException, ClassNotFoundException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        final Document read = new SAXReader().read(MyHost.getResourcesAsInputStream());
        //获取根节点
        final Element rootElement = read.getRootElement();
        //获取host节点
        final List<Element> hostElements = rootElement.selectNodes("//host");
        for (Element hostElement : hostElements) {
            //获取目录名
            final String path = hostElement.attributeValue("appBase");
            final String name = hostElement.attributeValue("name");
            //加载文件
            MyContext myContext = new MyContext(path);
            contextMap.put(name,myContext);
            myContext.init();
        }

    }

    private static InputStream getResourcesAsInputStream(){
        return MyHost.class.getClassLoader().getResourceAsStream("server.xml");
    }

    public Map<String, MyContext> getContextMap() {
        return contextMap;
    }

    public void setContextMap(Map<String, MyContext> contextMap) {
        this.contextMap = contextMap;
    }
}
