package com.npt.ces.cw.bean;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * author: owen
 * date:   2017/7/14 上午11:58
 * note:
 */
public class NptCWCountIntervals implements Serializable{

    public static final String VALUE_SEPARATOR = "#";
    public static final String ROW_SEPARATOR = ",";

    public NptCWCountIntervals(String str){
        if(!StringUtils.isEmpty(str)){
            try {
                String[] rows = str.split(ROW_SEPARATOR);
                intervalList = new ArrayList<>();
                for(String r:rows){
                     intervalList.add(new NptCWInterval(r));
                }
                loaded = true;
                return;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        loaded = false;
    }

    public class NptCWInterval{
        private Integer lowValue;
        private Integer upValue;
        private Integer score;

        public NptCWInterval(String val) throws Exception{
            String[] v = val.split(VALUE_SEPARATOR);
            lowValue = Integer.parseInt(v[0]);
            upValue = Integer.parseInt(v[1]);
            score = Integer.parseInt(v[2]);
        }

        public Integer getLowValue() {
            return lowValue;
        }

        public void setLowValue(Integer lowValue) {
            this.lowValue = lowValue;
        }

        public Integer getUpValue() {
            return upValue;
        }

        public void setUpValue(Integer upValue) {
            this.upValue = upValue;
        }

        public Integer getScore() {
            return score;
        }

        public void setScore(Integer score) {
            this.score = score;
        }

        public Boolean imHere(Integer v){
            return v >= lowValue && v <= upValue;
        }
    }

    private Boolean loaded;

    private List<NptCWInterval> intervalList;

    public List<NptCWInterval> getIntervalList() {
        return intervalList;
    }

    public void setIntervalList(List<NptCWInterval> intervalList) {
        this.intervalList = intervalList;
    }

    public Boolean getLoaded() {
        return loaded;
    }

    public void setLoaded(Boolean loaded) {
        this.loaded = loaded;
    }


    public Integer getScoreByValue(Integer v){
        Integer score = 0;

        if(this.loaded){
            for(NptCWInterval i:intervalList){
                if(i.imHere(v)){
                    score = i.getScore();
                    break;
                }
            }
        }

        return score;
    }
}
