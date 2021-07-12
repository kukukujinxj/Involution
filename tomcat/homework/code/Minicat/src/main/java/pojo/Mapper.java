package pojo;

import java.util.Map;

public class Mapper {
    private Map<String, Host> hostMap;

    public Map<String, Host> getHostMap() {
        return hostMap;
    }

    public void setHostMap(Map<String, Host> hostMap) {
        this.hostMap = hostMap;
    }

    public Mapper(Map<String, Host> hostMap) {
        this.hostMap = hostMap;
    }

    public Mapper() {
    }
}
