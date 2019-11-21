package com.tellhao.components_doc_tool.entity;

import java.util.List;

public class Treevo {
    private String id;
    private String text;
    private String pid;
    private int uid;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    private List<Treevo> children;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Treevo> getChildren() {
        return children;
    }

    public void setChildren(List<Treevo> children) {
        this.children = children;
    }
}
