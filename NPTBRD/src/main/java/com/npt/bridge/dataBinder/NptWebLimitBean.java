package com.npt.bridge.dataBinder;

/**
 * 项目: 柳州征信门户
 * 作者: zhanglei
 * 日期: 2017/6/30
 * 备注:
 */
public class NptWebLimitBean {
    private String method;
    private String source;
    private String target;

    public NptWebLimitBean(String method, String source, String target) {
        this.method = method;
        this.source = source;
        this.target = target;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
