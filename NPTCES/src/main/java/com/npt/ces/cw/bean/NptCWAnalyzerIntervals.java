package com.npt.ces.cw.bean;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * author: owen
 * date:   2017/7/14 上午11:58
 * note:
 */
public class NptCWAnalyzerIntervals implements Serializable{

    public static final String VALUE_SEPARATOR = "#";
    public static final String ROW_SEPARATOR = ",";

    public void setFormattedData(String str){
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

    public class NptCWInterval {
        private Double lowValue;
        private Double upValue;
        private Integer score;

        public NptCWInterval(String val) throws Exception{
            String[] v = val.split(VALUE_SEPARATOR);

            lowValue = Double.parseDouble(v[0]);
            upValue = Double.parseDouble(v[1]);
            score = Integer.parseInt(v[2]);
        }

        public Double getLowValue() {
            return lowValue;
        }

        public void setLowValue(Double lowValue) {
            this.lowValue = lowValue;
        }

        public Double getUpValue() {
            return upValue;
        }

        public void setUpValue(Double upValue) {
            this.upValue = upValue;
        }

        public Integer getScore() {
            return score;
        }

        public void setScore(Integer score) {
            this.score = score;
        }

        public Boolean imHere(Double v){
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


    public Integer getScoreByValue(Double v){
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
