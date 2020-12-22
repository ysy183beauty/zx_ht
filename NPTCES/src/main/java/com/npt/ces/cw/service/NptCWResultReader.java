package com.npt.ces.cw.service;

import com.npt.bridge.dataBinder.NptWebFieldDataArray;
import com.npt.bridge.dict.NptDict;
import com.npt.ces.cw.bean.NptCWResultDetailBox;
import com.npt.ces.cw.bean.NptCWRiskTendency;
import com.npt.ces.cw.entity.NptCWDmsResult;
import com.npt.ces.cw.entity.NptCWResult;
import com.npt.ces.cw.entity.NptCWRiskIndex;
import com.npt.ces.cw.entity.NptCWSubDmsResult;
import org.summer.extend.orm.Pagination;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * author: owen
 * date:   2017/7/6 上午10:47
 * note:
 *      预警结果读取接口
 */
public interface NptCWResultReader {


    Integer DEFAULT_TOP_NUM = 10;
    Integer LAST_DEFAULT_NUM = 24;

    /**
     *  author  : owen
     *  date    : 2017/7/7 下午3:01
     *  params  :
     *              [ceType]:信用实体的类型，为NULL则不限制信用实体的类型
     *              [num]:取值数量，为NULL默认取前10
     *  note    :
     *          读取信用预警结果的风险排名
     */
    Pagination<NptCWResult> listTopResults(NptDict ceType,Integer currentPage,Integer pageSize);

    Pagination<NptCWResult> listTopConcernResults(NptDict ceType,Long orgId, Integer currPage, Integer pageSize);

    /**
     *  author  : owen
     *  date    : 2017/7/7 下午3:07
     *  params  :
     *              [dmsId]:维度ID，取baseModelGroup的ID,为NULL则不限制维度
     *              [ceType]:信用实体的类型，为NULL则不限制信用实体的类型
     *              [num]:取值数量，为NULL默认取前10
     *  note    :
     *          读取信用预警维度结果的风险排名
     */
    Map<String, Collection<NptCWDmsResult>>  listTopDmsResults(Long dmsId,NptDict ceType,Integer num);

    /**
     *  author: zhanglei
     *  date:   2017/07/18 21:02
     *  note:
     *          列出维度
     */
    Collection<NptCWDmsResult> listDimensions(Integer ceTypeCode);

    Pagination<NptCWDmsResult> listTopDmsResults(Long dmsId, NptDict ceType, Integer currPage, Integer pageSize);

    /**
     *  author  : owen
     *  date    : 2017/7/7 下午3:09
     *  params  :
     *              [sdmsId]:维度ID，取baseModelPool的ID,为NULL则不限制维度
     *              [ceType]:信用实体的类型，为NULL则不限制信用实体的类型
     *              [num]:取值数量，为NULL默认取前10
     *  note    :
     *          读取信用预警子维度结果的风险排名
     */
    Map<String,Collection<NptCWSubDmsResult>> listTopSubDmsResults(Long sdmsId,NptDict ceType,Integer num);

    /**
     *  author  : owen
     *  date    : 2017/7/7 下午3:32
     *  params  :
     *              [ceid]:信用实体业务主键,比如统一信用代码，身份证号码
     *              []:
     *  note    :
     *          获取信用实体最后一批次的预警结果集装箱
     */
    NptCWResultDetailBox getCreditEntityResultBox(String ceId,NptDict ceType);

    List<NptCWRiskTendency> getCreditEntityTendency(String ceId,NptDict ceType);

    /**
     *  author  : owen
     *  date    : 2017/7/17 下午9:01
     *  params  :
     *              []:
     *              []:
     *  note    :
     *          加载最后指定数目的风险指数
     */
    List<NptCWRiskIndex> listLastRiskIndexs(Integer num);


    /**
     *  author: zhanglei
     *  date:   2017/07/17 20:10
     *  note:
     *          星标关注
     */
    void star(Long providerId, String creditEntityId, Integer creditEntityType);

    /**
     *  author: zhanglei
     *  date:   2017/07/17 20:10
     *  note:
     *          取消星标关注
     */
    void unStar(Long providerId, String creditEntityId, Integer creditEntityType);

    /**
     *  author: zhanglei
     *  date:   2017/07/19 10:22
     *  note:
     *          查询
     */
    Pagination<NptCWResult> search(NptDict ceType,String keyword, Integer currPage, Integer pageSize);

    /**
     *  author: zhanglei
     *  date:   2017/07/19 13:51
     *  note:
     *          是否星标关注
     */
    Boolean checkStar(Long userOrgId, String ceId);

    /**
     *  author: zhanglei
     *  date:   2017/07/19 14:03
     *  note:
     *          信用实体ID名称
     */
    String getCreditEntityIdTitle(NptDict ceType);

    String getCreditEntityTitle(NptDict ceType);

    NptWebFieldDataArray loadPoolDetail(Long dataTypeId, Long ukValue);
}
