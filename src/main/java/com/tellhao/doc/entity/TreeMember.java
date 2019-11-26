package com.tellhao.doc.entity;

import java.util.List;

public class TreeMember {
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

    private List<TreeMember> children;

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

    public List<TreeMember> getChildren() {
        return children;
    }

    public void setChildren(List<TreeMember> children) {
        this.children = children;
    }
}
