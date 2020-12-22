package com.npt.bridge.util;


import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.dict.NptDict;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 作者：owen
 * 时间：2016/11/9 17:17
 * 描述:
 */
public final class NptCommonUtil {

    public static String BLANK_DATA_PRESENT = "-";

    public static Long getDefaultParentId(){
        return -1L;
    }

    public static Date getCurrentSysDate(){
        return new Date();
    }

    public static String formatDate(Date date){
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public static String formatTimestamp(Timestamp ts){
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(ts);
    }

    public static java.sql.Timestamp getCurrentSysTimestamp(){
        return new java.sql.Timestamp(new java.util.Date().getTime());
    }

    public static Integer IntegerOne(){
        return 1;
    }

    public static Integer ConditionMax(){
        return 5;
    }

    public static Integer IntegerZero(){
        return 0;
    }

    public static Integer INTEGER_1 = 1;

    public static Integer INTEGER_0 = 0;

    public static String getActionReturnedAttributeName(){
        return "NPT_ACTION_RETURNED_JSON";
    }

    public static String getBaseModelGroupConditionTitle(){
        return "信息类别";
    }

    public static String getBaseModelProviderConditionTitle(){return "提供单位";}

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
     *作者：OWEN
     *时间：2016/11/10 23:12
     *描述:
     *      常量字典前缀集合
     */
    public enum NPT_DICT_PREFIX{
        PMS,RST,IDS,RAS,RAT,CST,FSS,FAL,LGA,LGB,BMH,BMC,CLD,SCT,DUB,BMHG,RPC,RPH,RPP,RPS,USER_AUTH,MPL,CSF,SOURCE
    }

    /**
     *作者：OWEN
     *时间：2016/11/10 22:51
     *描述:
     *      以前缀获取常量字典中的组
     */
    public static Collection<NptDict> getNptRmsDictGroupByPrefix(NPT_DICT_PREFIX prefix){
        Collection<NptDict> result = new ArrayList<NptDict>();
        NptDict allDict[] = NptDict.values();
        String _prefix = prefix.name() + "_";
        for(int i = 0;i < allDict.length;i++){
            if(allDict[i].name().startsWith(_prefix)){
                result.add(allDict[i]);
            }
        }
        return result;
    }

    /**
     *作者：OWEN
     *时间：2016/11/13 13:48
     *描述:
     *      获取指定前缀指定编号的唯一字典
     */
    public static NptDict getDict(NPT_DICT_PREFIX prefix, int code){
        Collection<NptDict> dicts = getNptRmsDictGroupByPrefix(prefix);
        if(null != dicts){
            for(NptDict dict:dicts){
                if(dict.getCode() == code){
                    return dict;
                }
            }
        }
        return null;
    }

    /**
     *作者：OWEN
     *时间：2016/11/16 22:55
     *描述:
     *
     */
    public static NptDict getDict(NPT_DICT_PREFIX prefix, String name){
        Collection<NptDict> dicts = getNptRmsDictGroupByPrefix(prefix);
        if(null != dicts){
            for(NptDict dict:dicts){
                if(dict.name().equals(name)){
                    return dict;
                }
            }
        }
        return null;
    }

    /**
     *作者：OWEN
     *时间：2016/11/16 23:37
     *描述:
     *
     */
    public static List<Long> getOrderObjectArrayBySeprator(String str,String sep){
        List<Long> temp = new ArrayList<>();
        if(null != str && null != sep){
            String strArray[] = str.split(sep);
            if(null != strArray && strArray.length > NptCommonUtil.IntegerZero()){
                for(String s:strArray){
                    try {
                        temp.add(Long.parseLong(s));
                    }catch (Exception e){
                        continue;
                    }
                }
            }
        }
        return temp;
    }

    public static Integer getApplyStandardFinishDays(){
        return 3;
    }

    public static Integer getApplyStandardDuringDays(){
        return 7;
    }

    public static String getMaxSubString(String s1, String s2) {
        String max, min;
        max = (s1.length() > s2.length()) ? s1 : s2;
        min = (max == s1) ? s2 : s1;
        for (int x = 0; x < min.length(); x++) {
            for (int y = 0, z = min.length() - x; z != min.length() + 1; y++, z++) {
                String temp = min.substring(y, z);
                if (max.contains(temp))
                    return temp;
            }
        }
        return "";
    }

    public static final Integer BMH_SPECIAL_MIN = 100;

    public static Collection<NptDict> getSpecialModelGroups(Integer cateType){
        Collection<NptDict> result = new ArrayList<NptDict>();
        Integer groupStart = cateType * 10 + 1;
        while (true){
            NptDict group = NptCommonUtil.getDict(NPT_DICT_PREFIX.BMHG,groupStart);
            if(null != group){
                result.add(group);
            }else {
                break;
            }
            groupStart ++;
        }
        return result;
    }


    public static void testLog(String info){
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");

        String strDate = dateFormat.format( now );

        System.out.println("<<TESTLOG>>在系统时间[" + strDate + "]触发了[" + info + "]");
    }
}
