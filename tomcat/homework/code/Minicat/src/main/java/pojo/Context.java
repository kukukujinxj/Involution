package pojo;

import server.HttpServlet;

import java.util.Map;

public class Context {
    private Map<String, HttpServlet> servletMap;

    public Map<String, HttpServlet> getServletMap() {
        return servletMap;
    }

    public void setServletMap(Map<String, HttpServlet> servletMap) {
        this.servletMap = servletMap;
    }

    public Context(Map<String, HttpServlet> servletMap) {
        this.servletMap = servletMap;
    }

    public Context() {
    }
}
