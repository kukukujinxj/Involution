package com.lagou.pojo;

public class MethodInfo {
    private String name;
    private long exeTime;
    private long endTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getExeTime() {
        return exeTime;
    }

    public void setExeTime(long exeTime) {
        this.exeTime = exeTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public MethodInfo(String name, long exeTime, long endTime) {
        this.name = name;
        this.exeTime = exeTime;
        this.endTime = endTime;
    }

    public MethodInfo() {
    }
}
