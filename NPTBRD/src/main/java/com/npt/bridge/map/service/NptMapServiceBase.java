package com.npt.bridge.map.service;

import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.arch.NptLogicDataType;
import com.npt.bridge.dataBinder.NptWebBridgeBean;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.map.bean.*;
import com.npt.bridge.model.NptBaseModel;
import com.npt.bridge.model.NptBaseModelLink;
import com.npt.bridge.model.NptBaseModelPool;
import com.npt.bridge.model.NptBaseModelPoolCdt;
import com.npt.bridge.util.NptCommonUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * author: owen
 * date:   2017/4/10 下午6:27
 * note:
 */
public abstract class NptMapServiceBase implements NptMapService {
    /**
     * author: owen
     * date:   2017/4/19 下午3:44
     * note:
     * 加载图谱结点的详细信息
     *
     * @param poolId
     * @param ukValue
     * @param bean
     */
    @Override
    public NptDict loadNodeDetail(Long poolId, String ukValue, NptWebBridgeBean bean) {
        return getNodeInfo(poolId,ukValue,bean);
    }

    /**
     * author: owen
     * date:   2017/4/11 下午9:16
     * note:
     * 通过关键字模糊查询
     *
     * @param model
     * @param searchValue
     */
    @Override
    public NptDict fuzzySearch(NptBaseModel model, String searchValue, NptWebBridgeBean bean) {
        return fuzzySearchFromMainPool(model, searchValue, bean);
    }

    /**
     * author: owen
     * date:   2017/4/11 下午8:17
     * note:
     * 加载模型的查询条件
     *
     * @param model
     */
    @Override
    public Collection<NptBaseModelPoolCdt> loadModelSearchConditions(NptBaseModel model) {
        return getBaseModelSearchConditions(model);
    }


    private NptMapPoint recurseGet(NptMapRecursionNode node,NptBaseMap bm){

        if (null != bm && null != node) {

            NptMapPoint fromPoint = node.convertToPoint();

            if (node.getChildCount() > NptCommonUtil.IntegerZero()) {

                Collection<NptMapRecursionLink> mls = node.getLinks();

                for (NptMapRecursionLink ml : mls) {

                    NptMapPoint toPoint = recurseGet(ml.getToNode(), bm);
                    if(null != toPoint) {
                        if (NptDict.MLR_RELATION.getCode() == ml.getLink().getRelLink()) {
                            toPoint.setHide(true);
                        }

                        NptMapLine line = new NptMapLine();
                        line.setTitle(ml.getTitle());
                        line.setDirection(NptMapLine.FORWARD);

                        bm.addSegment(new NptMapSegment(fromPoint, line, toPoint));
                    }
                }
            }
            return fromPoint;
        }
        return null;
    }


    @Override
    public void prepareForShow(NptBaseMap map,Boolean meargeLine) {

        if(null != map){
            meargeByHiddenPoint(map);

            if(meargeLine) {
                map.removeDuplication();
            }
        }

    }

    private void meargeByHiddenPoint(NptBaseMap map) {
        if(null != map){
            List<NptMapSegment> segments = map.getSegments();
            if(null != segments && !segments.isEmpty()){

                List<NptMapPoint> hideTailPoints = map.getHideTailPoint();
                if(null != hideTailPoints && !hideTailPoints.isEmpty()){

                    for(NptMapPoint mp:hideTailPoints){
                        List<NptMapSegment> mearged = map.mergeByMiddlePoint(mp);
                    }

                    meargeByHiddenPoint(map);
                }else {
                    return;
                }
            }
        }
    }

    /**
     * 作者：owen
     * 时间: 2017/4/28 15:37
     * 描述:
     * 由递归产生的图谱数据转换成普通的点线点集合
     *
     * @param map
     */
    @Override
    public NptBaseMap toBaseMap(NptRecursionMap map) {
        if(null == map){
            return null;
        }

        NptBaseMap baseMap = new NptBaseMap();

        NptMapRecursionNode rootNode = map.getRootNode();

        NptMapPoint rootPoint = rootNode.convertToPoint();

        baseMap.setRootPoint(rootPoint);
        baseMap.setSegments(new ArrayList<>());


        recurseGet(rootNode,baseMap);


        return baseMap;
    }

    /**
     * author: owen
     * date:   2017/4/10 下午6:28
     * note:
     * 基于模型创建图谱实体
     *
     * @param model
     */
    @Override
    public NptRecursionMap instance(NptBaseModel model, String ukValue) {

        /**
         * 创建一个图谱实体，用于装载本次的图谱内容
         */
        NptRecursionMap map = new NptRecursionMap();

        /**
         * 图谱的入口是一个主数据池
         */
        NptBaseModelPool mp = getBaseModelMainPool(model);
        if (null != mp && mp.getStatus().equals(NptDict.IDS_ENABLED.getCode())) {

            /**
             * 总体的图谱结点的ID集合，用于在递归时确认结点是否已存在
             */
            List<String> existedNodeId = new ArrayList<>();

            /**
             * 加载图谱数据
             */
            NptMapRecursionNode rootNode = loadMap(mp,ukValue,existedNodeId);

            /**
             * 获取到了图谱根结点，即表示图谱已被加载出来
             */
            if(null != rootNode) {
                map.setRootNode(rootNode);
                map.setResult(NptDict.RST_SUCCESS);
            }else {
                map.setResult(NptDict.RST_EXCEPTION("未找到图谱根结点的信息主体"));
            }
        }else {
            map.setResult(NptDict.RST_EXCEPTION("图谱模型不存在主数据池，查询失败"));
        }
        return map;
    }

    /**
     *  author: owen
     *  date:   2017/4/19 下午3:47
     *  note:
     *          加载图谱结点的基本信息
     */
    public abstract NptDict getNodeInfo(Long poolId,String ukValue,NptWebBridgeBean bean);

    /**
     * author: owen
     * date:   2017/4/11 下午9:17
     * note:
     */
    public abstract NptDict fuzzySearchFromMainPool(NptBaseModel model, String value, NptWebBridgeBean bean);

    /**
     * author: owen
     * date:   2017/4/11 下午8:18
     * note:
     * 加载模型的查询字段
     */
    public abstract Collection<NptBaseModelPoolCdt> getBaseModelSearchConditions(NptBaseModel model);

    /**
     * author: owen
     * date:   2017/4/11 下午2:07
     * note:
     * 获取数据池的外键链接
     */
    public abstract Collection<NptBaseModelLink> getPoolLinks(NptBaseModelPool p);

    /**
     * author: owen
     * date:   2017/4/10 下午8:25
     * note:
     * SELECT COUNT(1) FROM DATATYPE WHERE FIELD=VALUE
     */
    public abstract Integer selectCount(NptLogicDataType dataType, NptLogicDataField field, String value);

    /**
     * author: owen
     * date:   2017/4/10 下午8:22
     * note:
     * SELECT SEARCHFIELDS FROM DATATYPE WHERE FIELD=VALUE
     */
    public abstract List<Object> selectRows(
            NptLogicDataType dataType,
            Collection<NptLogicDataField> searchFields,
            NptLogicDataField field,
            String value,
            NptDict onlyEng);

    /**
     * author: owen
     * date:   2017/4/10 下午8:14
     * note:
     * 获取逻辑数据字段
     */
    public abstract NptLogicDataField getLogicDataFieldById(Long id);

    /**
     * author: owen
     * date:   2017/4/10 下午8:12
     * note:
     * 获取逻辑数据类别
     */
    public abstract NptLogicDataType getLogicDataTypeById(Long id);

    /**
     * author: owen
     * date:   2017/4/10 下午7:59
     * note:
     * 加载模型的索引字段
     */
    public abstract Collection<NptLogicDataField> getBaseModelPoolIndexFields(NptBaseModelPool p);

    /**
     * author: owen
     * date:   2017/4/10 下午7:37
     * note:
     * 获取模型的主数据池
     */
    public abstract NptBaseModelPool getBaseModelMainPool(NptBaseModel model);

    /**
     * author: owen
     * date:   2017/4/11 下午2:09
     * note:
     * 通过ID获取数据池信息
     */
    public abstract NptBaseModelPool getBaseModelPoolById(Long id);

    /**
     *  author: owen
     *  date:   2017/4/24 13:25
     *  note:
     *          加载逻辑字段的码表信息
     */
    public abstract Map<String,Map<String,String>> getFieldCodeValue(List<NptLogicDataField> fields);


    /**
     * author: owen
     * date:   2017/4/11 下午3:04
     * note:
     *      加载图谱实体数据
     */
    protected NptMapRecursionNode loadMap(NptBaseModelPool rootPool, String ukValue, List<String> nodeIds) {

        NptMapRecursionNode rootNode = null;

        /**
         * 首先保证加载参数正常可用
         */
        if (null != rootPool && !StringUtils.isBlank(ukValue) && null != nodeIds) {

            /**
             * 获取图谱模型主数据池的数据类别,要求数据类别状态正常
             */
            NptLogicDataType dataType = getLogicDataTypeById(rootPool.getDataTypeId());
            if (null != dataType && dataType.getStatus().equals(NptDict.IDS_ENABLED.getCode())) {

                /**
                 * 图谱的根结点是由数据主键来确定的，所以要保证数据主键字段正常
                 */
                NptLogicDataField ukField = getLogicDataFieldById(dataType.getUkFieldId());
                if (null != ukField && ukField.getStatus().equals(NptDict.IDS_ENABLED.getCode())) {

                    /**
                     * 尝试去加载图谱的根结点
                     */
                    List<NptMapRecursionNode> nodes = new ArrayList<>();
                    NptDict result = constructMapNode(rootPool, ukField, ukValue, nodeIds, nodes,null);

                    /**
                     * 加载根结点成功且数量是一个时，方为成功
                     */
                    if (NptDict.RST_SUCCESS.equals(result) && NptCommonUtil.IntegerOne().equals(nodes.size())) {

                        /**
                         * 集合中的第一个元素即为根结点
                         */
                        rootNode = nodes.iterator().next();
                        rootNode.setRoot(true);
                        /**
                         * 以根结点为入口，以递归的方式去加载根结点关联的其它结点及其它结点的其它结点
                         */
                        loadRelatedNode(rootNode, nodeIds);
                    }
                }
            }
        }
        return rootNode;
    }

    /**
     * author: owen
     * date:   2017/4/11 下午3:09
     * note:
     * 以递归的方式加载指定图谱结点的所有子结点
     */
    protected NptDict loadRelatedNode(NptMapRecursionNode parentNode, List<String> nodeIds) {

        NptDict result;

        if (null == parentNode || null == nodeIds) {
            result = NptDict.RST_INVALID_PARAMS;
        } else {

            /**
             * 加载关联结点的第一步是先获取上级结点的连接线
             *
             * 其中连接线内部会存储连接线的下级结点信息
             */
            result = constructMapLink(parentNode, nodeIds);

            if (NptDict.RST_SUCCESS.equals(result)) {

                /**
                 * 循环遍历每一条连接线，以递归的形式加载连接线下级结点的关联结点
                 */
                Collection<NptMapRecursionLink> mapLinks = parentNode.getLinks();
                if (null != mapLinks && !mapLinks.isEmpty()) {
                    for (NptMapRecursionLink ml : mapLinks) {
                        NptMapRecursionNode subNode = ml.getToNode();

                        result = loadRelatedNode(subNode, nodeIds);

                        if (!NptDict.RST_SUCCESS.equals(result)) {
                            break;
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * author: owen
     * date:   2017/4/11 下午3:18
     * note:
     *      在指定的数据池中通过指定的字段及字段值构造图谱结点
     */
    protected NptDict constructMapNode(NptBaseModelPool pool,
                                       NptLogicDataField field,
                                       String value,
                                       List<String> nodeIds,
                                       List<NptMapRecursionNode> nodeList,
                                       List<NptLogicDataField> addSearchFields) {

        NptDict result = NptDict.RST_SUCCESS;
        if (null == pool
                || !pool.getStatus().equals(NptDict.IDS_ENABLED.getCode())
                || null == field
                || !field.getStatus().equals(NptDict.IDS_ENABLED.getCode())
                || StringUtils.isBlank(value)
                || null == nodeIds
                || null == nodeList) {

            result = NptDict.RST_INVALID_PARAMS;
        } else {

            /**
             * 确定数据池对应的数据类别存在且状态正常
             */
            NptLogicDataType dataType = getLogicDataTypeById(pool.getDataTypeId());

            if (null != dataType && dataType.getStatus().equals(NptDict.IDS_ENABLED.getCode())) {

                /**
                 * 图谱结点的MAP_ROW中包含的字段为3+X
                 *
                 * 其中3为数据主键字段，业务主键字段，标题字段
                 * X为N个业务外键
                 */
                NptLogicDataField ukField = getLogicDataFieldById(dataType.getUkFieldId());
                NptLogicDataField titleField = getLogicDataFieldById(pool.getTitleFieldId());
                NptLogicDataField pkField = getLogicDataFieldById(pool.getPrimaryFieldId());

                if (null != ukField
                        && ukField.getStatus().equals(NptDict.IDS_ENABLED.getCode())
                        && null != titleField
                        && titleField.getStatus().equals(NptDict.IDS_ENABLED.getCode())
                        && null != pkField
                        && pkField.getStatus().equals(NptDict.IDS_ENABLED.getCode())) {

                    /**
                     * 三个关键字段
                     */
                    List<NptLogicDataField> searchFields = new ArrayList<>();
                    searchFields.add(ukField);
                    searchFields.add(titleField);
                    searchFields.add(pkField);

                    /**
                     * 添加辅助查询字段
                     */
                    if(null != addSearchFields && !addSearchFields.isEmpty()){
                        searchFields.addAll(addSearchFields);
                    }

                    /**
                     * N个业务外键
                     */
                    Collection<NptBaseModelLink> mpLinks = getPoolLinks(pool);
                    if (null != mpLinks && !mpLinks.isEmpty()) {
                        for (NptBaseModelLink bml : mpLinks) {
                            NptLogicDataField refField = getLogicDataFieldById(bml.getPoolRefKeyId());
                            if (null != refField && refField.getStatus().equals(NptDict.IDS_ENABLED.getCode())) {
                                searchFields.add(refField);
                            }
                        }
                    }

                    /**
                     * 查询
                     */
                    List<Object> searchResult = selectRows(
                            dataType,
                            searchFields,
                            field,
                            value,
                            NptDict.CST_ONLY_ENG);

                    /**
                     * 基于MAP_ROW构建N个图谱结点
                     */
                    if (null != searchResult && !searchResult.isEmpty()) {

                        for (Object row : searchResult) {
                            Map<String, Object> temp = (Map<String, Object>) row;
                            if (null != temp) {
                                NptMapNodeRowData mapRow = new NptMapNodeRowData();
                                mapRow.setUkField(ukField);
                                mapRow.setRowExisted(true);
                                mapRow.setDataType(dataType);

                                transCodeFieldValue(addSearchFields,temp);
                                mapRow.setKeyFieldValue(temp);

                                String rowValue = String.valueOf(temp.get(ukField.getFieldDbName()));
                                if (!StringUtils.isBlank(rowValue) && !NptCommonUtil.BLANK_DATA_PRESENT.equals(rowValue)) {
                                    String nodeId = dataType.getId() + rowValue;

                                    NptMapRecursionNode node = new NptMapRecursionNode();
                                    node.setMapRow(mapRow);
                                    node.setTitle(String.valueOf(temp.get(titleField.getFieldDbName())));
                                    node.setRoot(false);
                                    node.setNode(pool);
                                    node.setNodeId(nodeId);
                                    node.setUkValue(String.valueOf(temp.get(ukField.getFieldDbName())));
                                    nodeIds.add(nodeId);

                                    nodeList.add(node);
                                }
                            }
                        }
                    }
                }
            }
        }

        return result;
    }

    private void transCodeFieldValue(List<NptLogicDataField> fields,Map<String,Object> row){
        Map<String,Map<String,String>> fieldCV = getFieldCodeValue(fields);
        if(null != fieldCV && null != row){
            Set<String> codeFieldNames = fieldCV.keySet();
            Set<String> rowFieldNames = row.keySet();
            if(null != codeFieldNames && !codeFieldNames.isEmpty()){
                for(String cfn:codeFieldNames){
                    if(rowFieldNames.contains(cfn)){
                        String oldValue = String.valueOf(row.get(cfn));
                        String newValue = fieldCV.get(cfn).get(oldValue);
                        row.remove(cfn);
                        row.put(cfn,newValue);
                    }
                }
            }
        }
    }

    /**
     *  author: owen
     *  date:   2017/4/12 下午2:41
     *  note:
     *          读取上级结点的连接线，并读取连接级连接的下级结点信息
     */
    protected NptDict constructMapLink(NptMapRecursionNode fromNode, List<String> nodeIds) {

        NptDict result = NptDict.RST_SUCCESS;

        if (null == fromNode) {
            result = NptDict.RST_INVALID_PARAMS;
        } else {
            NptBaseModelPool fromPool = fromNode.getNode();
            List<NptMapRecursionLink> toLinks = new ArrayList<>();

            fromNode.setLinks(toLinks);

            if (null != fromPool && fromPool.getStatus().equals(NptDict.IDS_ENABLED.getCode())) {

                /**
                 * 图谱的结点线是基于模型跳接实现的，先获取结点对应数据库的模型跳接
                 */
                Collection<NptBaseModelLink> poolLinks = getPoolLinks(fromPool);

                if (null != poolLinks && !poolLinks.isEmpty()) {
                    for (NptBaseModelLink lk : poolLinks) {

                        /**
                         * 循环遍历每一条模型跳接，跳接的核心信息为：
                         *  源数据池 FROM_POOL
                         *  源数据字段 FROM_FIELD
                         *  目标数据池 TO_POOL
                         *  目标数据字段 TO_FIELD
                         */
                        NptBaseModelPool toPool = getBaseModelPoolById(lk.getToPoolId());
                        NptLogicDataField toField = getLogicDataFieldById(lk.getToKeyId());
                        NptLogicDataField fromField = getLogicDataFieldById(lk.getPoolRefKeyId());

                        /**
                         * 保存 FROM-TO四个核心属性存在且状态正常
                         */
                        if (null != toPool
                                && null != toField
                                && null != fromField
                                && toPool.getStatus().equals(toField.getStatus())
                                && toField.getStatus().equals(fromField.getStatus())
                                && toPool.getStatus().equals(NptDict.IDS_ENABLED.getCode())) {

                            /**
                             * MAP_ROW，指的是每个结点的入口信息，它由MAP进行封装，具体的格式为
                             *      <field_db_name,field_value>
                             *     这里包含三个核心字段:
                             *      数据主键，业务主键，标题字段
                             */
                            NptMapNodeRowData fromRow = fromNode.getMapRow();
                            if (null != fromRow) {
                                String toValue = String.valueOf(fromRow.getKeyFieldValue().get(fromField.getFieldDbName()));
                                if (!StringUtils.isBlank(toValue)) {

                                    List<NptMapRecursionNode> toNodes = new ArrayList<>();

                                    List<NptLogicDataField> addSearch = new ArrayList<>();
                                    NptLogicDataField titleField = getTitleField(lk.getLinkTitle());
                                    if(null != titleField){
                                        addSearch.add(titleField);
                                    }

                                    /**
                                     * 依据模型跳接的核心数据，加载实体结点
                                     *
                                     * 注意：每条跳接可能会找出N个结点，因为使用TO_FIELD和TO_VALUE可能会查到多条记录
                                     * 每条记录都会生成一个结点
                                     */
                                    result = constructMapNode(toPool, toField, toValue, nodeIds, toNodes,addSearch);

                                    if (NptDict.RST_SUCCESS.equals(result) && !toNodes.isEmpty()) {

                                        /**
                                         * 循环构建上级结点的连接线信息
                                         */
                                        for (NptMapRecursionNode tn : toNodes) {
                                            NptMapRecursionLink ml = new NptMapRecursionLink();
                                            ml.setToNode(tn);
                                            ml.setLink(lk);
                                            if(null != titleField){
                                                NptMapNodeRowData mr = tn.getMapRow();
                                                if(null != mr && null != mr.getKeyFieldValue()) {
                                                    ml.setTitle(String.valueOf(mr.getKeyFieldValue().get(titleField.getFieldDbName())));
                                                }else {
                                                    ml.setTitle(NptDict.RST_UNKNOW.getTitle());
                                                }
                                            }else {
                                                ml.setTitle(lk.getLinkTitle());
                                            }
                                            toLinks.add(ml);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return result;
    }

    /**
     *  author: owen
     *  date:   2017/4/24 12:14
     *  note:
     *          解析连接的标题
     *
     *          首先确定是否可以转为LONG，
     *              若可以则依据此LONG查询字段，然后依据字段是普通文本还是码表转换等确定其值
     *              若不可转为LONG或转为LONG但找不到对应的字段，则直接使用此文本
     */
    private NptLogicDataField getTitleField(String t){
        try {
            Long fieldId = Long.parseLong(t);
            return getLogicDataFieldById(fieldId);
        }catch (Exception e){

        }
        return null;
    }
}
