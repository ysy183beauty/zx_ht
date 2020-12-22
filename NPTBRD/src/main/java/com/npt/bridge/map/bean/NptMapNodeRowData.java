package com.npt.bridge.map.bean;

import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.arch.NptLogicDataType;

import java.io.Serializable;
import java.util.Map;

/**
 * author: owen
 * date:   2017/4/10 下午7:41
 * note:
 *      图谱中一行实体数据的描述信息
 */
public class NptMapNodeRowData implements Serializable{

    /**
     * 实体表中通过数据主键可以定位到唯一一行记录
     */
    private Boolean rowExisted;

    /**
     * 数据所属的数据类别
     */
    private NptLogicDataType dataType;

    /**
     * 数据类别的数据主键信息
     */
    private NptLogicDataField ukField;

    /**
     * 首次加载的关键字段的英文字段名和字段值
     */
    private Map<String,Object> keyFieldValue;


    public NptLogicDataType getDataType() {
        return dataType;
    }

    public void setDataType(NptLogicDataType dataType) {
        this.dataType = dataType;
    }

    public NptLogicDataField getUkField() {
        return ukField;
    }

    public void setUkField(NptLogicDataField ukField) {
        this.ukField = ukField;
    }

    public Map<String, Object> getKeyFieldValue() {
        return keyFieldValue;
    }

    public void setKeyFieldValue(Map<String, Object> keyFieldValue) {
        this.keyFieldValue = keyFieldValue;
    }

    public Boolean getRowExisted() {
        return rowExisted;
    }

    public void setRowExisted(Boolean rowExisted) {
        this.rowExisted = rowExisted;
    }
}
