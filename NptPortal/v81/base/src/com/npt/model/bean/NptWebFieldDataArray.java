package com.npt.model.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 项目: CreditPortal
 * 作者: 张磊
 * 日期: 17/2/16 上午9:52
 * 备注:
 */
public class NptWebFieldDataArray implements Serializable {
    public class NptWebFieldData{
        private Long fieldId;
        private String title;
        private String value;
        private String valueNote;
        private Integer linked;

        public Long getFieldId() {
            return fieldId;
        }

        public void setFieldId(Long fieldId) {
            this.fieldId = fieldId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getValueNote() {
            return valueNote;
        }

        public void setValueNote(String valueNote) {
            this.valueNote = valueNote;
        }

        public Integer getLinked() {
            return linked;
        }

        public void setLinked(Integer linked) {
            this.linked = linked;
        }
    }

    private Collection<NptWebFieldData> dataArray;
    private String uFieldValue;

    public Collection<NptWebFieldData> getDataArray() {
        return dataArray;
    }

    public void setDataArray(Collection<NptWebFieldData> dataArray) {
        this.dataArray = dataArray;
    }

    public NptWebFieldDataArray(){
        this.dataArray = new ArrayList<NptWebFieldData>();
    }

    public NptWebFieldData instanceFieldData(){
        return new NptWebFieldData();
    }

    public Collection<String> getTitleList(){
        Collection<String> list = new ArrayList<String>();
        for(NptWebFieldData data:dataArray){
            list.add(data.getTitle());
        }
        return list;
    }

    public String getuFieldValue() {
        return uFieldValue;
    }

    public void setuFieldValue(String uFieldValue) {
        this.uFieldValue = uFieldValue;
    }
}
