package com.npt.bridge.dataBinder;

import com.npt.bridge.model.NptBaseModelPoolCdt;
import com.npt.bridge.model.NptBaseModelTree;

import java.io.Serializable;
import java.util.*;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/10/12 15:01
 * 备注：
 */
public class NptWebBridgeBean<T> implements Serializable{

    public static final Integer DEFAULT_PAGE_SIZE = 10;
    public static final String GROUP_QUERY_CONDITION_NAME = "listByGroupId";
    public static final String PROVIDER_QUERY_CONDITION_NAME = "listByProviderId";

    public NptWebBridgeBean(){
        this.beginIndex = 0;
        this.totalPage = 0L;
        this.pageSize = NptWebBridgeBean.DEFAULT_PAGE_SIZE;
        this.totalCount = 0;
        this.currPage = 1;
        this.textBoxes = new ArrayList();
        this.dropBoxList = new ArrayList();
    }

    private List<Map<String,String>> queryCondition;
    private List<Map<String,String>> listCondition;
    private List<NptWebLimitBean> limitCondition;
    private Map<String,String> orderCondition;
    private Integer orderType;

    private List<NptBaseModelPoolCdt> webQueryCondition;
    private String webNote;

    private Long parentId;

    private Long primaryKeyId;

    private String primaryKeyValue;

    private Integer beginIndex;

    private Long totalPage;

    private Integer currPage;

    private Integer pageSize;

    private Integer totalCount;

    private String title;

    private List<NptWebDropBox<NptWebDropBoxElement>> dropBoxList;

    private List<NptWebTextBox> textBoxes;

    private Collection<String> columnTitles;
    private List<T> columnList;
    private Object dataList;

    private NptBaseModelTree modelTree;


    /**
     * 作者：owen
     * 日期：2016/10/25 10:17
     * 备注：
     *      获取页面传递的查询条件
     * 参数：
     * 返回：
     */
    public Map<String,String> getQueryCondition(){
        Map<String,String> result = new HashMap();
        if(null != queryCondition && !queryCondition.isEmpty()){
            //LIST里存储的是{name:field_name;value:field_value}的结构
            for(int i = 0;i < queryCondition.size();i++){
                //所以需要拿name的值作为新的键，以value作为新的键值
                Map<String,String> temp = queryCondition.get(i);
                //LIST里的每个Map长度必须是2，同上理，元素1是存储字段名，元素2是字段值
                if(2 == temp.size()){
                    List<String> keys = new ArrayList(temp.keySet());
                    String newKey = temp.get(keys.get(0));
                    String newValue = temp.get(keys.get(1));

                    if(null != newValue && !newValue.isEmpty()){
                        result.put(newKey,newValue);
                    }
                }
            }
        }
        return result;
    }


    public Map<String, String> getOrderCondition() {
        return orderCondition;
    }

    public void setOrderCondition(Map<String, String> orderCondition) {
        this.orderCondition = orderCondition;
    }

    public void setQueryCondition(List<Map<String, String>> queryCondition) {
        this.queryCondition = queryCondition;
    }

    public String getPrimaryKeyValue() {
        return primaryKeyValue;
    }

    public void setPrimaryKeyValue(String primaryKeyValue) {
        this.primaryKeyValue = primaryKeyValue;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
        this.totalPage =
                Long.valueOf(totalCount % (long)pageSize == 0L?totalCount / (long)pageSize:totalCount / (long)pageSize + 1L);
    }

    public List<NptWebDropBox<NptWebDropBoxElement>> getDropBoxList() {
        return dropBoxList;
    }

    public void setDropBoxList(List<NptWebDropBox<NptWebDropBoxElement>> dropBoxList) {
        this.dropBoxList = dropBoxList;
    }

    public List<NptWebTextBox> getTextBoxes() {
        return textBoxes;
    }

    public void setTextBoxes(List<NptWebTextBox> textBoxes) {
        this.textBoxes = textBoxes;
    }

    public List<T> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<T> columnList) {
        this.columnList = columnList;
    }


    public Object getDataList() {
        return dataList;
    }

    public void setDataList(Object dataList) {
        this.dataList = dataList;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getPrimaryKeyId() {
        return primaryKeyId;
    }

    public void setPrimaryKeyId(Long primaryKeyId) {
        this.primaryKeyId = primaryKeyId;
    }


    public Collection<String> getColumnTitles() {
        return columnTitles;
    }

    public void setColumnTitles(Collection<String> columnTitles) {
        this.columnTitles = columnTitles;
    }

    public Integer getBeginIndex() {
        return beginIndex;
    }

    public void setBeginIndex(Integer beginIndex) {
        this.beginIndex = beginIndex;
    }

    public Long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getCurrPage() {
        return currPage;
    }

    public void setCurrPage(Integer currPage) {
        this.currPage = currPage;
    }

    public List<Map<String, String>> getListCondition() {
        return listCondition;
    }

    public void setListCondition(List<Map<String, String>> listCondition) {
        this.listCondition = listCondition;
    }

    public Long getListCondition(String condition){
        if(null != listCondition && !listCondition.isEmpty()){
            for(Map<String,String> m:listCondition){
                if(m.containsKey(condition)){
                    try {
                        return Long.parseLong(m.get(condition));
                    }catch (NumberFormatException e){
                        return null;
                    }
                }
            }
        }
        return null;
    }

    public List<NptBaseModelPoolCdt> getWebQueryCondition() {
        return webQueryCondition;
    }

    public void setWebQueryCondition(List<NptBaseModelPoolCdt> webQueryCondition) {
        this.webQueryCondition = webQueryCondition;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public NptBaseModelTree getModelTree() {
        return modelTree;
    }

    public void setModelTree(NptBaseModelTree modelTree) {
        this.modelTree = modelTree;
    }


    public String getWebNote() {
        return webNote;
    }

    public void setWebNote(String webNote) {
        this.webNote = webNote;
    }

    public List<NptWebLimitBean> getLimitCondition() {
        return limitCondition;
    }

    public void setLimitCondition(List<NptWebLimitBean> limitCondition) {
        this.limitCondition = limitCondition;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }
}
