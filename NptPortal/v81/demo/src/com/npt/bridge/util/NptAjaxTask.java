package com.npt.bridge.util;

import java.io.Serializable;

/**
 * author: owen
 * date:   2017/4/20 下午4:28
 * note:
 */
public class NptAjaxTask implements Serializable{


    private String textInfo;
    private Boolean stop;
    private Boolean result;

    public NptAjaxTask(String initText){
        textInfo = initText;
        stop = false;
        result = true;
    }

    public String getTextInfo() {
        return textInfo;
    }

    public void setTextInfo(String textInfo) {
        this.textInfo = textInfo;
    }

    public Boolean getStop() {
        return stop;
    }

    public void setStop(Boolean stop) {
        this.stop = stop;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }
}
