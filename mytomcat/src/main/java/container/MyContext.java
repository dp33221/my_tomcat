package container;

import classloader.MyClassLoader;
import org.dom4j.DocumentException;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dingpei
 * @Description: todo
 * @date 2020/12/28 9:59 下午
 */
public class MyContext {
    //appName-wrapper 项目名-对应的wapper
    private Map<String,MyWrapper> wrapperMap =new HashMap<String,MyWrapper>();
    //项目路径
    private String path;

    private ClassLoader classLoader;

    public void init() throws DocumentException, IllegalAccessException, InstantiationException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException {
        this.classLoader=new MyClassLoader();
        File file = new File(path);
        final File[] files = file.listFiles();
        //加载每个项目
        for (File file1 : files) {
            MyWrapper myWrapper = new MyWrapper(file1.getPath(),classLoader);
            myWrapper.init();
            wrapperMap.put(file1.getName(),myWrapper);
        }

    }

    public MyContext(String path) {
        this.path = path;
    }

    public MyContext() {
    }

    public Map<String, MyWrapper> getWrapperMap() {
        return wrapperMap;
    }

    public void setWrapperMap(Map<String, MyWrapper> wrapperMap) {
        this.wrapperMap = wrapperMap;
    }
}
