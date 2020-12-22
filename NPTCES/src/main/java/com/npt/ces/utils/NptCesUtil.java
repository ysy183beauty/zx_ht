package com.npt.ces.utils;

import com.npt.bridge.util.NptCommonUtil;
import com.npt.ces.cw.bean.NptCWEfficacyGearList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * author: owen
 * date:   2017/7/7 下午4:45
 * note:
 */
public final class NptCesUtil {

    public static final String tailChar = "0123456789";
    public static final Integer processTimesPerCycle = 7;

    /**
     *  author  : owen
     *  date    : 2017/7/7 下午5:30
     *  params  :
     *              []:
     *              []:
     *  note    :
     *          获取当前时间点（以星期几为单位）要处理的结尾字符集合
     */
    public static final List<Integer> getWeekTailChar(Integer wd){
        List<Integer> result = new ArrayList<>();
        switch (wd){
            case 0:
                result.add(0);
                result.add(1);
                break;
            case 1:
                result.add(2);
                break;
            case 2:
                result.add(3);
                break;
            case 3:
                result.add(4);
                break;
            case 4:
                result.add(5);
                break;
            case 5:
                result.add(6);
                result.add(7);
                break;
            case 6:
                result.add(8);
                result.add(9);
                break;
            default:
        }
        return result;
    }


    /**
     *  author  : owen
     *  date    : 2017/7/7 下午5:52
     *  params  :
     *              []:
     *              []:
     *  note    :
     *          生成处理批次号，结构
     *          模型ID#日期
     */
    public static final String generateBatchNO(Long modelId){

        Calendar cal=Calendar.getInstance();
        cal.setTime(new Date());
        int weekNo = cal.get(Calendar.WEEK_OF_YEAR);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        return String.valueOf(year + "#" + month + "-" + day);
    }

    /**
     *  author  : owen
     *  date    : 2017/7/14 下午2:46
     *  params  :
     *              []:
     *              []:
     *  note    :
     *          功效计算得分
     *
     *
     *          指标得分=基础分+调整分
     *
     *          调整分=  （实际值-本档标准值)/(上档标准值-本档标准值)*(上档基础分-本档基础分)
     *
     */
    public static final Integer efficacyCompute(Double realValue, NptCWEfficacyGearList gearList){
        Integer finalScore = 0;

        if(null != realValue && null != gearList && gearList.getLoaded()){

            Integer[] gearNo = gearList.getRealDataGearNo(realValue);

            Integer basicScore = NptCommonUtil.IntegerZero();
            Double adjustScore = 0d;

            if(gearNo[0] >= NptCommonUtil.IntegerZero()) {
                //正常落在2-5区间
                NptCWEfficacyGearList.NptCWEfficacyGear preGear = gearList.locateGear(gearNo[0]);
                NptCWEfficacyGearList.NptCWEfficacyGear curGear = gearList.locateGear(gearNo[1]);

                if (null != preGear && null != curGear) {
                    basicScore = curGear.getBasicScore();
                    adjustScore = ((realValue - curGear.getStandardValue())
                                    / (preGear.getStandardValue() - curGear.getStandardValue()))
                                    * (preGear.getBasicScore() - curGear.getBasicScore());
                }
            }else {
                //落在1区间
                NptCWEfficacyGearList.NptCWEfficacyGear firstGear = gearList.locateGear(0);
                if(null != firstGear){
                    basicScore = firstGear.getBasicScore();
                    adjustScore = (firstGear.getThreshold() - realValue)/firstGear.getThreshold()*firstGear.getBasicScore();
                }
            }

            finalScore = basicScore + (int) Math.rint(adjustScore);
        }

        return finalScore;
    }

    public static final String getRiskLevelByScore(Integer score){
        if(score < 5){
            return "G";
        }else if(score >= 5 && score < 10){
            return "F";
        }else if(score >= 10 && score < 20){
            return "E";
        }else if(score >= 20 && score < 35){
            return "D";
        }else if(score >= 35 && score < 50){
            return "C";
        }else if(score >= 50 && score < 75){
            return "B";
        }else{
            return "A";
        }
    }




    public static void main(String[] args) throws Exception {
    }
}
