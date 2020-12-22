package com.npt.bridge.dataBinder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * 项目：NPTWebApp
 * 作者：OWEN
 * 时间：2016/12/3 22:42
 * 描述:
 */
public class NptWebFieldDataArray implements Serializable{
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
        this.dataArray = new ArrayList();
    }

    public NptWebFieldData instanceFieldData(){
        return new NptWebFieldData();
    }

    public Collection<String> getTitleList(){
        Collection<String> list = new ArrayList();
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


    public NptWebFieldData getFieldDataByFieldId(Long id){
        if(null != this.getDataArray() && !this.getDataArray().isEmpty()){
            for(NptWebFieldData fd:this.getDataArray()){
                if(fd.getFieldId().equals(id)){
                    return fd;
                }
            }
        }
        return null;
    }

    public void resetData(Map<String,Object> values,String ufv){
        if(null != values && !values.isEmpty()){
            if(null == dataArray){
                dataArray = new ArrayList<>();
            }else {
                dataArray.clear();
            }

            values.forEach((k,v) -> {
                NptWebFieldData fieldData = new NptWebFieldData();
                fieldData.setValue(String.valueOf(v));
                fieldData.setTitle(k);
                dataArray.add(fieldData);
            });

            uFieldValue = ufv;
        }
    }
}
