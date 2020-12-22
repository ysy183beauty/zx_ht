package com.npt.web.action;

import cn.com.ikdo.style.sitemesh3.AbstractPaginationAction;

import java.io.Serializable;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/9/26 12:34
 * 备注：
 *      基于Struts 2.0的支持分页的控制器
 */
public abstract class NptPaginationAction<T extends Serializable> extends AbstractPaginationAction<T> {

    public static final String PAGINATION_BEGIN = "beginIndex";
    public static final String PAGINATION_PAGESIZE= "pageSize";
    public static final String PAGINATION_CURRENTPAGE= "currPage";
    public static final String WEB_PARENT_ID = "parentId";
    public static final String WEB_CONDITION = "webInvokeCondition";
    public static final String WEB_PRIMARY_KEY_VALUE = "primaryKeyValue";
    public static final String WEB_PRIMARY_KEY = "primaryKey";


    /**
     *方法:
     *参数:
     *返回:
     *作者: owen
     *时间: 2016/9/26 12:43
     *备注:获取从前台传递的分页起始值
     */
    public Integer getPageBeginIndex(){
        Integer beginIndex = this.getIntParameter(NptPaginationAction.PAGINATION_BEGIN);
        Integer currPage = this.getIntParameter(NptPaginationAction.PAGINATION_CURRENTPAGE);
        Integer pageSize = this.getIntParameter(NptPaginationAction.PAGINATION_PAGESIZE);

        currPage = currPage == null ? 1 : currPage;
        pageSize = pageSize == null ? this.configPageSize() : pageSize;

        return  (beginIndex == null ?  ((currPage - 1) * pageSize + 1) : beginIndex) - 1;
    }

    /**
     *方法:
     *参数:
     *返回:
     *作者: owen
     *时间: 2016/9/26 12:44
     *备注:获取从前台传递的分页大小
     */
    public Integer getPageSize(){
        Integer pageSize = this.getIntParameter(NptPaginationAction.PAGINATION_PAGESIZE);

        return pageSize == null ? this.configPageSize() : pageSize;
    }

    /**
     *方法:
     *参数:
     *返回:
     *作者: owen
     *时间: 2016/9/26 12:44
     *备注:获取从前台传递的当前页面序号
     */
    public Integer getPageCurrentPage(){
        Integer currPage = this.getIntParameter(NptPaginationAction.PAGINATION_CURRENTPAGE);

        return currPage == null ? 1 : currPage;
    }

    /**
     * 作者：97175
     * 日期：2016/10/9 17:22
     * 备注：
     *      
     * 参数：
     * 返回：
     */
    public Long getWebParentId(){
        Long parentId = this.getLongParameter(NptPaginationAction.WEB_PARENT_ID);
        
        return parentId;
    }


    public String getWebCondition(){
        return this.getParameter(NptPaginationAction.WEB_CONDITION);
    }

    public String getPrimaryKeyValue(){
        return this.getParameter(NptPaginationAction.WEB_PRIMARY_KEY_VALUE);
    }

    public Long getPrimaryKeyId(){
        return this.getLongParameter(NptPaginationAction.WEB_PRIMARY_KEY);
    }
}
