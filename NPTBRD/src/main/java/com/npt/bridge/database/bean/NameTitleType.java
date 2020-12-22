package com.npt.bridge.database.bean;

/**
 * 项目: 〔大理〕征信
 * 作者: zhanglei
 * 日期: 2017/4/26
 * 备注:
 */
public class NameTitleType {
    public NameTitleType(String n, String t,String p,String l){
        this.name = n;
        this.title = t;
        this.type = p;
        try {
            this.len = Integer.parseInt(l);
        }catch (NumberFormatException e){
            this.len = 256;
        }
    }
    public String name;
    public String title;
    public String type;
    public Integer len;
}
