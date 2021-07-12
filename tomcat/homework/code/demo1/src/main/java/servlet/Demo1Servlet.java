package servlet;

import server.HttpProtocolUtil;
import server.HttpServlet;
import server.Request;
import server.Response;

import java.io.IOException;

public class Demo1Servlet extends HttpServlet {
    public void init() throws Exception {

    }

    public void destory() throws Exception {

    }

    public void doGet(Request request, Response response) {
        String content = "<h1>Demo1Servlet get</h1>";
        try {
            response.output((HttpProtocolUtil.getHttpHeader200(content.getBytes().length) + content));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doPost(Request request, Response response) {
        String content = "<h1>Demo1Servlet post</h1>";
        try {
            response.output((HttpProtocolUtil.getHttpHeader200(content.getBytes().length) + content));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
