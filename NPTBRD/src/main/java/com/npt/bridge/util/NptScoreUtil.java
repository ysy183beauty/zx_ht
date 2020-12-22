package com.npt.bridge.util;

import com.npt.bridge.model.NptBaseModelPoolStatistic;

import java.util.Collection;

/**
 * 项目： NPTWebApp
 * 作者： owen
 * 时间： 2017/5/24 16:44
 * 描述：
 */
public class NptScoreUtil {


    public enum  BS_GRADE{
        GREAT,//优秀
        GOOD,//良好
        NORMAL,//正常
        POOR,//较差
        BAD//极差
    }

    public static final Integer BUSINESS_STANDARD_SCORE = 500;

    /**
     *作者：owen
     *时间: 2017/5/24 16:45
     *描述:
     *      企业依据数据池评分的简易评分算法
     */
    public static Integer businessSimpleScore(Collection<NptBaseModelPoolStatistic> ss){

        Long tempScore = 0L;

        if(null != ss && !ss.isEmpty()){

            for(NptBaseModelPoolStatistic s:ss){
                tempScore += s.getDataCount() * s.getPoolScore();
            }
        }
        Integer finalScore = BUSINESS_STANDARD_SCORE + tempScore.intValue();

        return finalScore < 0?0:finalScore;
    }

    /**
     *作者：owen
     *时间: 2017/5/24 16:55
     *描述:
     *      企业简单评级
     */
    public static BS_GRADE businessSimpleGrade(Integer score){
        if(score > 700){
            return BS_GRADE.GREAT;
        }else if(score > 550 && score <= 700){
            return BS_GRADE.GOOD;
        }else if (score > 450 && score <= 550){
            return BS_GRADE.NORMAL;
        }else if (score > 300 && score < 450){
            return BS_GRADE.POOR;
        }else if(score <= 300){
            return BS_GRADE.BAD;
        }else {
            return BS_GRADE.NORMAL;
        }
    }
}
