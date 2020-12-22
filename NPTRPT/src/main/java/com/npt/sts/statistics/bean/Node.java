package com.npt.sts.statistics.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目: NPTSTS
 * 作者: 张磊
 * 日期: 16/10/25 下午10:9
 * 备注: 简单树形结构
 */
public class Node {

    private String id = null;
    private String name = null;
    private List<Node> children = null;

    private Map<String, Node> keyMap = new HashMap<String, Node>();

    public Node(String id) {
        this.id = id;
    }

    public Node(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Node insert(String id) {
        return insert(id, id);
    }

    public Node insert(String id, String name) {
        Node child = null;
        if (children == null) {
            children = new ArrayList<Node>();
        }

        if (keyMap.get(id) == null) {
            child = new Node(id, name);
            children.add(child);
            keyMap.put(id, child);
        } else {
            return keyMap.get(id);
        }

        return child;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public Map<String, Node> getKeyMap() {
        return keyMap;
    }
}
