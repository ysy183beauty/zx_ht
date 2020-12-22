package com.npt.rms.base;

import com.npt.bridge.dict.NptBusinessCode;
import com.npt.bridge.dict.NptDict;
import com.npt.rms.log.entity.NptLog;
import com.npt.rms.remote.entity.NptRemoteSystem;
import org.summer.extend.orm.Condition;
import org.summer.extend.orm.Pagination;

import java.util.Collection;
import java.util.Date;

/**
 * 项目：NPTWebApp
 * 作者：OWEN
 * 时间：2016/11/9 23:05
 * 描述:
 */
public interface NptRmsCommonService {

    /**
     *作者：OWEN
     *时间：2016/11/28 16:49
     *描述:
     *      创建日志类并设置通用属性
     */
    NptLog makeLog();

    /**
     *作者：OWEN
     *时间：2016/11/28 16:49
     *描述:
     *      保存日志信息
     */
    void save(NptLog log);


    Pagination<NptLog> findAllLog(Integer beginIndex, Integer pageSize, Condition... conditions);

    /**
     *作者：OWEN
     *时间：2016/11/28 16:50
     *描述:
     *      通过操作类别获取远程系统信息
     */
    Collection<NptRemoteSystem> getRemoteSystemByActionType(NptDict type);

    /**
     *作者：owen
     *时间：2016/12/15 11:24
     *描述:
     *      获取外部查询系统的信息
     */
    void updateRemoteSystem(String ip,Integer port);

    /**
     *作者：OWEN
     *时间：2016/11/28 17:02
     *描述:
     *      通过码类型获取码集合
     */
    Collection<NptBusinessCode> getBusinessCodeByType(Long codeType);

    /**
     *作者：OWEN
     *时间：2016/11/28 17:03
     *描述:
     *      获取某一类型下的指定码值的码信息
     */
    NptBusinessCode getBusinessCode(String codeValue,Long codeType);

    /**
     *  author: owen
     *  date:   2017/4/18 下午3:20
     *  note:
     *          加载所有码信息
     */
    Collection<NptBusinessCode> listAllCode();

    /**
     *  author: owen
     *  date:   2017/4/24 13:34
     *  note:
     *          加载字段的码表信息
     */
    Collection<NptBusinessCode> listFieldCodes(Long fieldId);

    /**
     *  author: owen
     *  date:   2017/4/18 下午3:21
     *  note:
     *          加载增量码信息
     */
    Collection<NptBusinessCode> listIncrementCode(Date date);

    void updateBusinessCodeIncrementStamp(Date date);
}
