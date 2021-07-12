package pojo;

import java.util.Map;

public class Host {
    private Map<String, Context> contextMap;

    public Map<String, Context> getContextMap() {
        return contextMap;
    }

    public void setContextMap(Map<String, Context> contextMap) {
        this.contextMap = contextMap;
    }

    public Host(Map<String, Context> contextMap) {
        this.contextMap = contextMap;
    }

    public Host() {
    }
}
