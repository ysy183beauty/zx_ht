package com.npt.rms.util;

import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.util.NptCommonUtil;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.summer.extend.security.PlatformSecurityContext;

/**
 * 作者：97175
 * 时间：2016/11/9 17:17
 * 描述:
 */
public final class NptRmsUtil {

    public static Long getCurrentUserId(){
        if(PlatformSecurityContext.isLogined()){
            return (Long) PlatformSecurityContext.getCurrentOperator().getOperatorId();
        }else {
            return NptCommonUtil.getDefaultParentId();
        }
    }

    public static String getCurrentUserName(){
        if(PlatformSecurityContext.isLogined()){
            return PlatformSecurityContext.getCurrentOperator().getName();
        }else {
            return NptDict.RST_UNKNOW.getTitle();
        }
    }


    public static String encodePassword(String pwd){
        MessageDigestPasswordEncoder encoder = new MessageDigestPasswordEncoder("SHA-1", true);
        return encoder.encodePassword(pwd, null);
    }

    public static Integer businessList(NptLogicDataField a,NptLogicDataField b){
        if(null == a.getAlias() || null == b.getAlias()){
            return NptCommonUtil.IntegerZero();
        }

        String maxSub = NptCommonUtil.getMaxSubString(a.getAlias(),b.getAlias());

        return maxSub.length();
    }
}
