package domain;

import utils.HttpProtocalUtils;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author dingpei
 * @Description: todo
 * @date 2020/12/28 11:26 上午
 */
public class Response {
    private OutputStream outputStream;

    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void outputError() throws IOException {
        final String s = HttpProtocalUtils.setHttp404();
        outputStream.write(s.getBytes());
    }

    public void output(String output) throws IOException {
        outputStream.write(output.getBytes());
        outputStream.flush();
    }

    public Response() {
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

}
