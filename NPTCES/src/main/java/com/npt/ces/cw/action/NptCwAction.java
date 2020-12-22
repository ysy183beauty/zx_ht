package com.npt.ces.cw.action;

import com.alibaba.fastjson.JSON;
import com.npt.bridge.dataBinder.NptWebFieldDataArray;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.ces.cw.auth.NptCWAuthSerivce;
import com.npt.ces.cw.auth.NptCWUser;
import com.npt.ces.cw.bean.NptCWResultDetailBox;
import com.npt.ces.cw.bean.NptCWRiskTendency;
import com.npt.ces.cw.entity.NptCWDmsResult;
import com.npt.ces.cw.entity.NptCWResult;
import com.npt.ces.cw.entity.NptCWRiskIndex;
import com.npt.ces.cw.service.NptCWResultReader;
import com.npt.web.action.NptPaginationAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.summer.extend.orm.Pagination;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 项目: 〔大理〕征信
 * 作者: zhanglei
 * 日期: 2017/7/11
 * 备注:
 */

public abstract class NptCwAction extends NptPaginationAction {

    @Autowired
    private NptCWResultReader cwResultReader;

    @Autowired
    private NptCWAuthSerivce authSerivce;


    public abstract NptDict getCreditEntityType();


    /**
     *  author: zhanglei
     *  date:   2017/07/11 15:41
     *  note:
     *          首页
     */
    public String index() {


        // 企业信用风险TOP10
        List<NptCWResult> top10R = new ArrayList<>();
        Pagination<NptCWResult> bsTopResults = cwResultReader.listTopResults(getCreditEntityType(), null,null);
        if(null != bsTopResults && bsTopResults.getTotalCount() > 0) {
            top10R.addAll(bsTopResults.getResults());
        }
        this.setAttribute("bsTopResults", top10R);

        // 本局星标关注TOP10
        List<NptCWResult> top10CR = new ArrayList<>();
        NptCWUser cwUser = authSerivce.getCurrentUser();
        if(null != cwUser) {
            Pagination<NptCWResult> concernResults = cwResultReader.listTopConcernResults(getCreditEntityType(),cwUser.getUserOrgId(), null,null);
            if(null != concernResults && concernResults.getTotalCount() > 0) {
                top10CR.addAll(concernResults.getResults());
            }
        }
        this.setAttribute("concernResults", top10CR);

        // 企业预警维度名称TOP10
        Map<String, Collection<NptCWDmsResult>> bsDmsTopReuslt = cwResultReader.listTopDmsResults(null,getCreditEntityType(),null);
        this.setAttribute("bsDmsTopReuslt", bsDmsTopReuslt);

        setAttr();
        return SUCCESS;
    }

    private void setAttr() {
        String creditEntityIT = cwResultReader.getCreditEntityIdTitle(this.getCreditEntityType());
        this.setAttribute("creditEntityIT", creditEntityIT);
        String creditEntityTT = cwResultReader.getCreditEntityTitle(this.getCreditEntityType());
        this.setAttribute("creditEntityTT", creditEntityTT);
    }


    public String detail(){
        String ceId = getParameter("creditEntityId");
        this.setAttribute("ceId", ceId);

        NptCWResultDetailBox detailBox = cwResultReader.getCreditEntityResultBox(ceId,getCreditEntityType());

        if(null != detailBox && detailBox.getReady()){
            this.setAttribute("creditEntityDetailBox",detailBox);
        }

        NptCWUser cwUser = authSerivce.getCurrentUser();
        if (null != cwUser) {
            Boolean star = cwResultReader.checkStar(cwUser.getUserOrgId(), ceId);
            this.setAttribute("star", star);
        }

        return SUCCESS;
    }

    public void analyzeTendency(){

        String ceId = getParameter("creditEntityId");

        List<NptCWRiskTendency> result = cwResultReader.getCreditEntityTendency(ceId,getCreditEntityType());

        if(null != result && !result.isEmpty()){
            this.ajaxOutPutJson(JSON.toJSONString(result));
        }
    }

    public void listRiskIndex(){
        List<NptCWRiskIndex> result = cwResultReader.listLastRiskIndexs(null);

        this.ajaxOutPutJson(JSON.toJSONString(result));
    }


    /**
     *  author: zhanglei
     *  date:   2017/07/11 16:41
     *  note:
     *          信用风险首页
     */
    public String topIndex() {
        // get ces type code list
        Collection<NptDict> ceTypeList = NptCommonUtil.getNptRmsDictGroupByPrefix(NptCommonUtil.NPT_DICT_PREFIX.BMH_WARN);
        this.setAttribute("CES_TYPE_LIST", ceTypeList);

        // get ces dms list
        Collection<NptCWDmsResult> dmsList = cwResultReader.listDimensions(this.getCreditEntityType().getCode());
        this.setAttribute("CES_DMS_LIST", dmsList);

        setAttr();
        return SUCCESS;
    }

    /**
     *  author: zhanglei
     *  date:   2017/07/11 15:42
     *  note:
     *          信用风险列表
     */
    public String topList() {
        Integer ceTypeCode = this.getCreditEntityType().getCode();
        Long dmsId = this.getLongParameter("dmsId");
        Integer currPage = getPageCurrentPage();
        Integer pageSize = getPageSize();

        NptDict ceType = NptCommonUtil.getDict(NptCommonUtil.NPT_DICT_PREFIX.BMH,ceTypeCode);
        if (dmsId == null) {
            Pagination<NptCWResult> dataPagination = cwResultReader.listTopResults(ceType, currPage, pageSize);
            this.setAttribute("dataPagination", createPaginationResult(dataPagination, currPage, pageSize));
        } else {
            Pagination<NptCWDmsResult> dataPagination = cwResultReader.listTopDmsResults(dmsId, ceType, currPage, pageSize);
            this.setAttribute("dataPagination", createPaginationResult(dataPagination, currPage, pageSize));
        }

        return SUCCESS;
    }

    /**
     *  author: zhanglei
     *  date:   2017/07/19 10:49
     *  note:
     *          星标首页
     */
    public String starIndex() {
        setAttr();
        return SUCCESS;
    }

    /**
     *  author: zhanglei
     *  date:   2017/07/19 10:48
     *  note:
     *          星标列表
     */
    public String starList() {
        Integer currPage = getPageCurrentPage();
        Integer pageSize = getPageSize();

        NptCWUser cwUser = authSerivce.getCurrentUser();
        if (null != cwUser) {
            Pagination<NptCWResult> dataPagination = cwResultReader.listTopConcernResults(getCreditEntityType(),cwUser.getUserOrgId(), currPage, pageSize);
            this.setAttribute("dataPagination", createPaginationResult(dataPagination, currPage, pageSize));
        }
        return SUCCESS;
    }

    /**
     *  author: zhanglei
     *  date:   2017/07/19 10:34
     *  note:
     *          查询首页
     */
    public String searchIndex() {
        setAttr();
        return SUCCESS;
    }

    /**
     *  author: zhanglei
     *  date:   2017/07/19 10:20
     *  note:
     *          查询列表
     */
    public String searchList() {
        String keyword = this.getParameter("keyword");
        this.setAttribute("keyword", keyword);
        Integer currPage = getPageCurrentPage();
        Integer pageSize = getPageSize();
        Pagination<NptCWResult> dataPagination = cwResultReader.search(getCreditEntityType(),keyword, currPage, pageSize);
        this.setAttribute("dataPagination", createPaginationResult(dataPagination, currPage, pageSize));
        return SUCCESS;
    }

    /**
     *  author: zhanglei
     *  date:   2017/07/17 20:16
     *  note:
     *          星标关注
     */
    public void star() {
        try {
            String creditEntityId = this.getParameter("creditEntityId");
            Integer creditEntityType = this.getCreditEntityType().getCode();
            NptCWUser cwUser = authSerivce.getCurrentUser();
            Long providerId = cwUser.getUserOrgId();
            cwResultReader.star(providerId, creditEntityId, creditEntityType);
            this.outputSuccessResult();
        } catch (Exception e) {
            this.outputErrorResult(e.getMessage() == null ? e.toString() : e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     *  author: zhanglei
     *  date:   2017/07/17 20:16
     *  note:
     *          取消星标关注
     */
    public void unStar() {
        try {
            String creditEntityId = this.getParameter("creditEntityId");
            Integer creditEntityType = this.getCreditEntityType().getCode();
            NptCWUser cwUser = authSerivce.getCurrentUser();
            Long providerId = cwUser.getUserOrgId();
            cwResultReader.unStar(providerId, creditEntityId, creditEntityType);
            this.outputSuccessResult();
        } catch (Exception e) {
            this.outputErrorResult(e.getMessage() == null ? e.toString() : e.getMessage());
            e.printStackTrace();
        }
    }

    public String poolDetail() {
        try {
            Long uFieldValue = this.getLongParameter("uFieldValue");
            Long dataTypeId = this.getLongParameter("dataTypeId");
            NptWebFieldDataArray dataArray = cwResultReader.loadPoolDetail(dataTypeId, uFieldValue);
            this.setAttribute("DATA_ARRAY", dataArray);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }
}
