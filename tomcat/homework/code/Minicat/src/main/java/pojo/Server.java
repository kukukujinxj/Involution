package pojo;

import java.util.Map;

public class Server {
    private Map<String, Mapper> serviceMap;

    public Map<String, Mapper> getServiceMap() {
        return serviceMap;
    }

    public void setServiceMap(Map<String, Mapper> serviceMap) {
        this.serviceMap = serviceMap;
    }

    public Server(Map<String, Mapper> serviceMap) {
        this.serviceMap = serviceMap;
    }

    public Server() {
    }
}
