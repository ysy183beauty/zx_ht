package com.npt.ces.cw.bean;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * author: owen
 * date:   2017/7/14 下午12:01
 * note:
 */
public class NptCWEfficacyGearList implements Serializable{


    public NptCWEfficacyGearList(String str){
        if(!StringUtils.isEmpty(str)){
            try {

                String[] svs = str.split(NptCWAnalyzerIntervals.ROW_SEPARATOR);
                gears = new NptCWEfficacyGear[svs.length];

                for(int i = 0;i < svs.length;i++){

                    String[] st = svs[i].split(NptCWAnalyzerIntervals.VALUE_SEPARATOR);

                    NptCWEfficacyGear g = new NptCWEfficacyGear(i + 1,i * 20,Float.parseFloat(st[0]),Float.parseFloat(st[1]));
                    gears[i] = g;
                }
                loaded = true;
                return;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        loaded = false;
    }

    public class NptCWEfficacyGear{
        //档号
        private Integer gearNO;
        //基础分
        private Integer basicScore;
        //标准值
        private Float standardValue;
        //阈值
        private Float threshold;

        public NptCWEfficacyGear(Integer no,Integer bs,Float sv,Float th){
            gearNO = no;
            basicScore = bs;
            standardValue = sv;
            threshold = th;
        }

        public Integer getGearNO() {
            return gearNO;
        }

        public void setGearNO(Integer gearNO) {
            this.gearNO = gearNO;
        }

        public Integer getBasicScore() {
            return basicScore;
        }

        public void setBasicScore(Integer basicScore) {
            this.basicScore = basicScore;
        }

        public Float getStandardValue() {
            return standardValue;
        }

        public void setStandardValue(Float standardValue) {
            this.standardValue = standardValue;
        }

        public Float getThreshold() {
            return threshold;
        }

        public void setThreshold(Float threshold) {
            this.threshold = threshold;
        }
    }

    private NptCWEfficacyGear[] gears;
    private Boolean loaded;

    public NptCWEfficacyGear[] getGears() {
        return gears;
    }

    public void setGears(NptCWEfficacyGear[] gears) {
        this.gears = gears;
    }

    public Boolean getLoaded() {
        return loaded;
    }

    public void setLoaded(Boolean loaded) {
        this.loaded = loaded;
    }


    /**
     *  author  : owen
     *  date    : 2017/7/14 下午3:27
     *  params  :
     *              []:
     *              []:
     *  note    :
     *          获取实际际的档号，档号从1开始以保证每档都有上档
     */
    public final Integer[] getRealDataGearNo(Double rd){
        Integer[] gearNo = {-1,-1};

        if(getLoaded()){
            for(int i = 1;i < gears.length;i++){
                if(rd >= gears[i-1].getThreshold() && rd < gears[i].getThreshold()){
                    //上档号
                    gearNo[0] = i - 1;
                    //当前档号
                    gearNo[1] = i;
                }
            }
        }

        return gearNo;
    }

    public NptCWEfficacyGear locateGear(Integer location){
        try {
            return gears[location];
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
