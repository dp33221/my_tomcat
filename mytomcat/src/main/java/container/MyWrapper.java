package container;

import classloader.MyClassLoader;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import servlet.Servlet;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dingpei
 * @Description: todo
 * @date 2020/12/28 9:59 下午
 */
public class MyWrapper {
    // url-servlet url对应的servlet
    private Map<String, Servlet> servletMap =new HashMap<String, Servlet>();
    private MyClassLoader classLoader;
    private String path;
    public void init() throws DocumentException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        final File file = new File(path + "/classes/web.xml");
        final Document read = new SAXReader().read(file);
        final Element rootElement = read.getRootElement();
        final List<Element> elements = rootElement.selectNodes("//servlet");
        for (int i = 0; i < elements.size(); i++) {
            Element element=elements.get(i);
            Element servletNameElement=(Element)element.selectSingleNode("servlet-name");
            final String servletName = servletNameElement.getStringValue();
            Element servletClassElement =(Element)element.selectSingleNode("servlet-class");
            final String servletClass = servletClassElement.getStringValue();
            final Element servletMapping = (Element)rootElement.selectSingleNode("/web/servlet-mapping[servlet-name='" + servletName + "']");
            final String urlPattern = servletMapping.selectSingleNode("url-pattern").getStringValue();
            final String[] split = servletClass.split("\\.");
            classLoader.setPath(path+"/classes/"+split[0]+"/");
            servletMap.put(urlPattern, (Servlet) classLoader.loadClass(split[1]).getConstructor().newInstance());
        }


    }

    public MyWrapper(String path,ClassLoader classLoader) {
        this.path = path;
        this.classLoader=(MyClassLoader)classLoader;
    }

    public MyWrapper() {
    }

    public Map<String, Servlet> getServletMap() {
        return servletMap;
    }

    public void setServletMap(Map<String, Servlet> servletMap) {
        this.servletMap = servletMap;
    }


}
