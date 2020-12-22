package com.npt.util;

import com.npt.arch.entity.NptLogicDataField;
import com.npt.dict.NptDict;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 12:30
 * 描述:
 */
public class NptUtil {


    /*************************************************************************/
    public static final Integer INTEGER_0 = 0;
    public static final Integer INTEGER_1 = 1;



    public static final Integer BMH_SPECIAL_MIN = 100;


    public static Long getDefaultParentId(){
        return -1L;
    }
    /*************************************************************************/

    /**
     *作者：owen
     *时间：2017/1/17 15:53
     *描述:
     *      根据字段建立SQL查询字段列表
     */
    public static String getFieldString(Collection<NptLogicDataField> fields, String sep, NptDict dict){
        List<String> columnNames = new ArrayList<String>();

        Iterator<NptLogicDataField> iterator = fields.iterator();
        List<Long> idList = new ArrayList<Long>();

        while (iterator.hasNext()){
            NptLogicDataField field = iterator.next();
            if(!idList.contains(field.getId())){
                if(dict == NptDict.CST_ENG_AS_CHN) {
                    columnNames.add(field.getFieldDbName() + " AS \"" + field.getAlias().replaceAll("[\\pP‘’“”]", "") + "\"");
                }else if(dict == NptDict.CST_ONLY_CHN){
                    columnNames.add("\"" + field.getAlias().replaceAll("[\\pP‘’“”]", "") + "\"");
                }else if(dict == NptDict.CST_ONLY_ENG){
                    columnNames.add(field.getFieldDbName());
                }

                idList.add(field.getId());
            }
        }

        return StringUtils.join(columnNames.toArray(),sep);
    }

    /**
     *作者：owen
     *时间：2017/1/17 15:54
     *描述:
     *      获取服务器当前时间
     */
    public static Date getCurrentSysDate(){
        return new Date();
    }

    /**
     *作者：OWEN
     *时间：2016/11/10 23:12
     *描述:
     *      常量字典前缀集合
     */
    public enum NPT_DICT_PREFIX{
        PMS,RST,IDS,RAS,RAT,CST,FSS,FAL,LGA,LGB,BMH,BMC,CLD,SCT,DUB,BMHG,RPC,RPH,RPP,RPS
    }
}
