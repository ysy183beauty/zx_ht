package com.npt.bridge.util;

/**
 * Created by dlms on 2016/7/14.
 */
public class StringUtils {

    /**
     * @Method: getStr
     * @Description: TODO(获得操作名称)
     * @return String 返回类型
     * @throws
     */
    public static String getStr(Object key) {

        return key==null?"":key.toString();
    }

    /**
     * @Method: getInt
     * @Description: TODO(获得操作名称)
     * @return int 返回类型
     * @throws
     */
    public static int getInt(Object key) {
        int value = 0;
        String valueStr = getStr(key);
        if(!"".equals(valueStr)){
            value = Integer.parseInt(valueStr);
        }
        return value;
    }
    /**
     * @Method: getFloat
     * @Description: TODO(获得float类型的数据)
     * @return float 返回类型
     * @throws
     */
    public static float getFloat(Object key) {
        float value = 0;
        String valueStr = getStr(key);
        if(!"".equals(valueStr)){
            value = Float.parseFloat(valueStr);
        }
        return value;
    }
}
