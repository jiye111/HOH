package com.example.zhang.hoh.bean.list;

public class listBottomInRVBean {
    String name;
    String local;
    int src;
    boolean isCollected;

    public listBottomInRVBean(String name, String local, int src, boolean isCollected) {
        this.name = name;
        this.local = local;
        this.src = src;
        this.isCollected = isCollected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public int getSrc() {
        return src;
    }

    public void setSrc(int src) {
        this.src = src;
    }

    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
    }
}
