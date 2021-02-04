package classloader;

import java.io.*;

/**
 * @author dingpei
 * @Description: todo
 * @date 2020/12/29 11:33 上午
 */
public class MyClassLoader extends ClassLoader {
    private String path;

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String classPath = path+name+".class";
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(classPath);
            outputStream = new ByteArrayOutputStream();
            int temp = 0;
            while((temp = inputStream.read()) != -1){
                outputStream.write(temp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        byte[] bytes = outputStream.toByteArray();
//        return defineClass(name,bytes,0,bytes.length);
        return defineClass(outputStream.toByteArray() , 0 , outputStream.toByteArray().length) ;

    }
}
