package com.npt.sts.statistics.action;

import com.npt.bridge.base.NptBaseEntity;
import com.npt.bridge.dict.NptDict;
import com.npt.sts.statistics.entity.NptStsIndex;
import com.npt.sts.statistics.manager.NptStsIndexManager;
import com.npt.web.action.NptPaginationAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.summer.extend.orm.Pagination;
import org.summer.extend.orm.condition.Conditions;


/**
 * 项目: NPTSTS
 * 作者: 张磊
 * 日期: 16/11/29 上午10:40
 * 备注: 统计分析首页
 */
@Controller("npt.sts.statistics.index")
public class NptStatisticsIndexAction extends NptPaginationAction<NptStsIndex> {
    @Autowired
    private NptStsIndexManager indexManager;

    /**
     * 作者: 张磊
     * 日期: 16/11/29 上午10:54
     * 备注: 首页
     */
    public String index() {
        return list();
    }

    public String list() {
        Pagination<NptStsIndex> dataPagination = this.indexManager.findAll(getPageBeginIndex(), getPageSize(), Conditions.eq(NptBaseEntity.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()), Conditions.asc(NptBaseEntity.PROPERTY_DISPLAY_ORDER));
        this.setAttribute("dataPagination", createPaginationResult(dataPagination, getPageCurrentPage(), getPageSize()));
        return SUCCESS;
    }

    /**
     * 作者: 张磊
     * 日期: 16/11/29 下午5:36
     * 备注: 获取初始分页参数
     */
    @Override
    public Integer getPageSize() {
        Integer pageSize = this.getIntParameter(NptPaginationAction.PAGINATION_PAGESIZE);

        return pageSize == null ? 9 : pageSize;
    }
}
