package com.npt.sts.statistics.action;

import com.alibaba.fastjson.JSON;
import com.npt.bridge.arch.NptLogicDataProvider;
import com.npt.bridge.arch.NptLogicDataType;
import com.npt.bridge.dict.NptBusinessCode;
import com.npt.bridge.dict.NptDict;
import com.npt.rms.arch.service.NptRmsArchService;
import com.npt.rms.base.NptRmsCommonService;
import com.npt.rms.base.action.NptRMSAction;
import com.npt.sts.statistics.bean.Node;
import com.npt.sts.statistics.bean.SimpleNode;
import com.npt.sts.statistics.entity.NptStsData;
import com.npt.sts.statistics.manager.NptStsDataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.summer.commons.lang.DateFormatUtils;
import org.summer.extend.orm.condition.Conditions;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 项目: NPTSTS
 * 作者: 张磊
 * 日期: 16/10/25 下午8:13
 * 备注:
 */
@Controller("npt.sts.statistics")
public class NptStatisticsAction extends NptRMSAction {

    @Autowired
    private NptStsDataManager stsDataManager;
    @Resource(name = "rmsCommonService")
    private NptRmsCommonService rmsCommonService;

    @Resource(name = "rmsArchService")
    private NptRmsArchService rmsArchService;

    /**
     * 作者: 张磊
     * 日期: 16/10/25 下午8:17
     * 备注: 首页
     */
    public String index() {
        //Collection<NptLogicDataProvider> orgList = null;
        List<NptLogicDataProvider> data=new ArrayList<>();
       /* NptLogicDataProvider rootOrg = rmsArchService.getRootOrg();
        if (rootOrg != null) {
            orgList = rmsArchService.listOrg(rootOrg.getId());
        }*/
        List<Map> list=stsDataManager.getOrgName();
        for(Map map:list){
            NptLogicDataProvider entity=new NptLogicDataProvider();
            entity.setId(Long.parseLong(map.get("ORG_ID").toString()));
            entity.setOrgName(map.get("ORG_NM").toString());
            data.add(entity);
        }
        //this.setAttribute("_ORG_LIST",orgList);
        this.setAttribute("_ORG_LIST",data);
        return list();
    }

    /**
     * 作者: 张磊
     * 日期: 16/10/28 上午11:40
     * 备注: 入库总量统计列表
     */
    public String list() {
        String orgId = getParameterNotEmpty("orgId");
        String subId = getParameterNotEmpty("subId");
        String startDate = getParameterNotEmpty("startDate");
        String endDate = getParameterNotEmpty("endDate");
        if (!valid(startDate, endDate)) return ERROR;
        Node node = new Node("ROOT");
        List<Map> list = stsDataManager.getStatistics(orgId, subId, startDate, endDate);
        int maxCount = stsDataManager.getStatisticsMax(orgId, subId, startDate, endDate);
        for (Map map : list) {
            node.insert(String.valueOf(map.get("ORG_ID")), String.valueOf(map.get("ORG_NM")))
                    .insert(String.valueOf(map.get("SUB_ID")), String.valueOf(map.get("SUB_NAME")))
                    .insert(String.valueOf(map.get("TABLE_TYPE")), String.valueOf(map.get("TABLE_NAME")))
                    .insert(String.valueOf(map.get("COUNT")))
                    .insert(
                            String.valueOf(
                                    ((BigDecimal) map.get("COUNT"))
                                            .divide(BigDecimal.valueOf(maxCount), 2, BigDecimal.ROUND_HALF_DOWN)
                                            .multiply(BigDecimal.valueOf(100))
                                            .intValue())
                    );
        }
        this.setAttribute("DATALIST", node.getChildren());
        this.setAttribute("STARTDATE", stsDataManager.formatDateString(startDate, "-"));
        this.setAttribute("ENDDATE", stsDataManager.formatDateString(endDate, "-"));
        return SUCCESS;
    }

    private String getParameterNotEmpty(String name) {
        String orgId = this.getParameter(name);
        if (orgId != null) {
            if (orgId.isEmpty() || orgId.equals("-1")) orgId = null;
        }
        return orgId;
    }

    /**
     * 作者: 张磊
     * 日期: 16/10/28 上午11:39
     * 备注: 验证时间格式, 通过返回 true
     */
    private boolean valid(String startDate, String endDate) {
        try {
            if (startDate != null && endDate != null) {
                SimpleDateFormat sdf;
                if (startDate.length() == 8 && endDate.length() == 8) {
                    sdf = new SimpleDateFormat("yyyyMMdd");
                    sdf.parse(startDate);
                    sdf.parse(endDate);
                } else if (startDate.length() == 6 && endDate.length() == 6) {
                    sdf = new SimpleDateFormat("yyyyMM");
                    sdf.parse(startDate);
                    sdf.parse(endDate);
                } else if (startDate.length() == 4 && endDate.length() == 4) {
                    sdf = new SimpleDateFormat("yyyy");
                    sdf.parse(startDate);
                    sdf.parse(endDate);
                } else {
                    throw new Exception("时间格式不统一");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 作者: 张磊
     * 日期: 16/10/28 上午11:40
     * 备注: 信息量变化详情列表
     */
    public String detail() {
        try {
            String id = getParameter("id");
            String startDate = this.getParameter("startDate");
            String endDate = this.getParameter("endDate");

            if (!valid(startDate, endDate)) return ERROR;
            NptLogicDataType dataType = rmsArchService.findDataTypeById(Long.parseLong(id));
            NptLogicDataProvider org = rmsArchService.findOrgById(dataType.getParentId());
            this.setAttribute("DATATYPE", dataType);
            this.setAttribute("ORG", org);
            this.setAttribute("DATALIST", stsDataManager.getDetails(id, startDate, endDate));
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    /**
     * 作者: 张磊
     * 日期: 16/10/28 上午11:41
     * 备注: 各单位荣誉/失信信息统计
     */
    public String method2() {
        return SUCCESS;
    }

    /**
     * 作者: 张磊
     * 日期: 16/12/5 下午4:43
     * 备注: template/statistics/失信信息统计.ftl
     */
    public void list2() {
        String startDate = this.getParameter("startDate");
        String endDate = this.getParameter("endDate");
        if (!valid(startDate, endDate)) {
            this.outputErrorResult("时间格式不统一");
            return;
        }

        // 设置默认值
        if (startDate == null) startDate = "20160101";
        if (endDate == null) endDate = new SimpleDateFormat("yyyyMMdd").format(new Date());

        List<Map> rysxCount = stsDataManager.getRysxCount(startDate);
        Node rysxCountNode = new Node("ROOT");
        for (Map map : rysxCount) {
            rysxCountNode.insert(String.valueOf(map.get("ORG_ID")), String.valueOf(map.get("ORG_NM")))
                    .insert(String.valueOf(map.get("SUB_ID")), String.valueOf(map.get("SUB_NAME")))
                    .insert(String.valueOf(map.get("CODE_VALUE")), String.valueOf(map.get("CODE_NAME")))
                    .insert(String.valueOf(((BigDecimal) map.get("TOTAL_AMOUNT")).intValue()));
        }
        List<Map> rysxList = stsDataManager.getRysxList(startDate, endDate);

        if (rysxList.isEmpty()) {
            this.outputErrorResult("暂无数据");
            return;
        }

        Node rysxListNode = new Node("ROOT");
        for (Map map : rysxList) {
            rysxListNode.insert(String.valueOf(map.get("ORG_ID")), String.valueOf(map.get("ORG_NM")))
                    .insert(String.valueOf(map.get("SUB_ID")), String.valueOf(map.get("SUB_NAME")))
                    .insert(String.valueOf(map.get("CODE_VALUE")), String.valueOf(map.get("CODE_NAME")))
                    .insert(String.valueOf(((BigDecimal) map.get("TOTAL_AMOUNT")).intValue()));
        }
        List<String> orgList = new ArrayList<>();
        // '企业荣誉信息','个人荣誉信息','企业失信信息','个人失信信息'
        List<Integer> qyry = new ArrayList<>();
        List<Integer> grry = new ArrayList<>();
        List<Integer> qysx = new ArrayList<>();
        List<Integer> grsx = new ArrayList<>();

        for (Node node : rysxListNode.getChildren()) {
            orgList.add(node.getName());
            qyry.add(getNodeValue(rysxCountNode.getKeyMap().get(node.getId()), "106101", "100100") + getNodeValue(node, "106101", "100100"));
            grry.add(getNodeValue(rysxCountNode.getKeyMap().get(node.getId()), "106102", "100100") + getNodeValue(node, "106102", "100100"));
            qysx.add(getNodeValue(rysxCountNode.getKeyMap().get(node.getId()), "106101", "100101") + getNodeValue(node, "106101", "100101"));
            grsx.add(getNodeValue(rysxCountNode.getKeyMap().get(node.getId()), "106102", "100101") + getNodeValue(node, "106102", "100101"));
        }

        Map<String, Object> result = new HashMap<>();
        result.put("ORG", orgList);
        result.put("QYRY", qyry);
        result.put("GRRY", grry);
        result.put("QYSX", qysx);
        result.put("GRSX", grsx);

        this.outputSuccessResult(JSON.toJSONString(result));
    }

    /**
     * 作者: 张磊
     * 日期: 16/10/31 下午2:56
     * 备注: 获取(企业/个人)(荣誉/失信)数据量
     */
    private Integer getNodeValue(Node node, String nodeId1, String nodeId2) {
        if (node == null) return 0;
        Node node1 = node.getKeyMap().get(nodeId1);
        if (node1 == null) return 0;
        Node node2 = node1.getKeyMap().get(nodeId2);
        if (node2 == null) return 0;
        return Integer.parseInt(node2.getChildren().get(0).getName());
    }

    public String method3() {
        return SUCCESS;
    }

    public String method4() {
        return SUCCESS;
    }

    public String method5() {
        return SUCCESS;
    }

    public String method6() {
        return SUCCESS;
    }

    public String method7() {
        return SUCCESS;
    }

    public String method8() {
        return SUCCESS;
    }

    public String method9() {
        System.out.println("ddd");
        return SUCCESS;
    }

    /**
     * 单位行政效能考评结果详情--单位名称信息
     * @return
     */
    public String method10() {
        /*Collection<NptLogicDataProvider> orgList = null;
        NptLogicDataProvider rootOrg = rmsArchService.getRootOrg();
        if (rootOrg != null) {
            orgList = rmsArchService.listOrg(rootOrg.getId());
        }
        this.setAttribute("_ORG_LIST",orgList);
        return SUCCESS;*/
        List<NptLogicDataProvider> orgList=new ArrayList<>();
        Integer stsStypeId=10;
        List<Map> list=stsDataManager.selectUnitNameList(stsStypeId);
        for(Map m:list){
            NptLogicDataProvider entity=new NptLogicDataProvider();
            entity.setId(Long.parseLong(m.get("ORG_ID").toString()));
            entity.setOrgName(m.get("ORG_NM").toString());
            orgList.add(entity);
        }
        this.setAttribute("_ORG_LIST",orgList);
        return SUCCESS;
    }

    public String method11() {
        return SUCCESS;
    }

    /**
     * 作者: 张磊
     * 日期: 16/12/5 下午3:25
     * 备注: template/统计分析/企业数据统计/区域划分统计/各区域企业占比统计.ftl
     */
    public void list3() {
        Map<String, String> result = new HashMap<>();

        Integer tjType = this.getIntParameter("tjType");
        if (tjType == null) {
            tjType = 100100;
        }
        Date startDate = this.getDateTimeParameter("startDate", DateFormatUtils.D_yyyyMMdd);
        if (startDate == null) {
            startDate = new Date(1000000000000L);
        }
        Date endDate = this.getDateTimeParameter("endDate", DateFormatUtils.D_yyyyMMdd);
        if (endDate == null) {
            endDate = new Date();
        }
        Collection<NptBusinessCode> codeList = rmsCommonService.getBusinessCodeByType(-104L);
        for (NptBusinessCode code : codeList) {
            Number sumByCondition = stsDataManager.getSumByCondition("sjl", Conditions.eq("stsTypeId", 3L), Conditions.eq("creditType", tjType), Conditions.eq("stsCode", code.getCodeValue()), Conditions.ge("dataTime", new java.sql.Timestamp(startDate.getTime())), Conditions.le("dataTime", new java.sql.Timestamp(endDate.getTime())));
            result.put(code.getCodeName(), sumByCondition.toString());
        }

        this.outputSuccessResult(JSON.toJSONString(result));
    }

    /**
     * 作者: 张磊
     * 日期: 16/12/5 下午3:29
     * 备注: template/统计分析/企业数据统计/区域划分统计/各地区荣誉信息、失信信息占比统计.ftl
     */
    public void list4() {
        Map<String, String> result = new HashMap<>();

        Date startDate = this.getDateTimeParameter("startDate", DateFormatUtils.D_yyyyMMdd);
        if (startDate == null) {
            startDate = new Date(1000000000000L);
        }
        Date endDate = this.getDateTimeParameter("endDate", DateFormatUtils.D_yyyyMMdd);
        if (endDate == null) {
            endDate = new Date();
        }
        Collection<NptBusinessCode> codeList = rmsCommonService.getBusinessCodeByType(-105L);
        for (NptBusinessCode code : codeList) {
            Number rySum = stsDataManager.getSumByCondition("sjl", Conditions.eq("stsTypeId", 4L), Conditions.eq("creditType", 100100), Conditions.eq("stsCode", code.getCodeValue()), Conditions.ge("dataTime", new java.sql.Timestamp(startDate.getTime())), Conditions.le("createTime", new java.sql.Timestamp(endDate.getTime())));
            Number sxSum = stsDataManager.getSumByCondition("sjl", Conditions.eq("stsTypeId", 4L), Conditions.eq("creditType", 100101), Conditions.eq("stsCode", code.getCodeValue()), Conditions.ge("dataTime", new java.sql.Timestamp(startDate.getTime())), Conditions.le("createTime", new java.sql.Timestamp(endDate.getTime())));
            result.put(code.getCodeName(), rySum.toString() + "," + sxSum.toString());
        }

        this.outputSuccessResult(JSON.toJSONString(result));
    }

    /**
     * 作者: 张磊
     * 日期: 16/12/5 下午3:30
     * 备注: template/统计分析/企业数据统计/性质分类统计/各类型企业占比统计.ftl
     */
    public void list5() {
        Map<String, String> result = new HashMap<>();

        Integer tjType = this.getIntParameter("tjType");
        if (tjType == null) {
            tjType = 100100;
        }
        Date startDate = this.getDateTimeParameter("startDate", DateFormatUtils.D_yyyyMMdd);
        if (startDate == null) {
            startDate = new Date(1000000000000L);
        }
        Date endDate = this.getDateTimeParameter("endDate", DateFormatUtils.D_yyyyMMdd);
        if (endDate == null) {
            endDate = new Date();
        }
        Collection<NptBusinessCode> codeList = rmsCommonService.getBusinessCodeByType(-101L);
        for (NptBusinessCode code : codeList) {
            Number sumByCondition = stsDataManager.getSumByCondition("sjl", Conditions.eq("stsTypeId", 5L), Conditions.eq("creditType", tjType), Conditions.eq("stsCode", code.getCodeValue()), Conditions.ge("dataTime", new java.sql.Timestamp(startDate.getTime())), Conditions.le("dataTime", new java.sql.Timestamp(endDate.getTime())));
            result.put(code.getCodeName(), sumByCondition.toString());
        }

        this.outputSuccessResult(JSON.toJSONString(result));
    }

    /**
     * 作者: 张磊
     * 日期: 16/12/5 下午3:30
     * 备注: template/统计分析/企业数据统计/行业分类统计/各行业企业占比统计.ftl
     */
    public void list6() {
        Map<String, String> result = new HashMap<>();

        Integer tjType = this.getIntParameter("tjType");
        if (tjType == null) {
            tjType = 100100;
        }
        Date startDate = this.getDateTimeParameter("startDate", DateFormatUtils.D_yyyyMMdd);
        if (startDate == null) {
            startDate = new Date(1000000000000L);
        }
        Date endDate = this.getDateTimeParameter("endDate", DateFormatUtils.D_yyyyMMdd);
        if (endDate == null) {
            endDate = new Date();
        }
        Collection<NptBusinessCode> codeList = rmsCommonService.getBusinessCodeByType(-102L);
        for (NptBusinessCode code : codeList) {
            Number sumByCondition = stsDataManager.getSumByCondition("sjl", Conditions.eq("stsTypeId", 6L), Conditions.eq("creditType", tjType), Conditions.eq("stsCode", code.getCodeValue()), Conditions.ge("dataTime", new java.sql.Timestamp(startDate.getTime())), Conditions.le("dataTime", new java.sql.Timestamp(endDate.getTime())));
            result.put(code.getCodeName(), sumByCondition.toString());
        }

        this.outputSuccessResult(JSON.toJSONString(result));
    }

    /**
     * 作者: 张磊
     * 日期: 16/12/5 下午3:31
     * 备注: template/统计分析/企业数据统计/规模分类统计/各规模企业占比统计.ftl
     */
    public void list7() {
        Map<String, String> result = new HashMap<>();

        Integer tjType = this.getIntParameter("tjType");
        if (tjType == null) {
            tjType = 100100;
        }
        Date startDate = this.getDateTimeParameter("startDate", DateFormatUtils.D_yyyyMMdd);
        if (startDate == null) {
            startDate = new Date(1000000000000L);
        }
        Date endDate = this.getDateTimeParameter("endDate", DateFormatUtils.D_yyyyMMdd);
        if (endDate == null) {
            endDate = new Date();
        }
        Collection<NptBusinessCode> codeList = rmsCommonService.getBusinessCodeByType(-103L);
        for (NptBusinessCode code : codeList) {
            Number sumByCondition = stsDataManager.getSumByCondition("sjl", Conditions.eq("stsTypeId", 7L), Conditions.eq("creditType", tjType), Conditions.eq("stsCode", code.getCodeValue()), Conditions.ge("dataTime", new java.sql.Timestamp(startDate.getTime())), Conditions.le("dataTime", new java.sql.Timestamp(endDate.getTime())));
            result.put(code.getCodeName(), sumByCondition.toString());
        }

        this.outputSuccessResult(JSON.toJSONString(result));
    }

    /**
     * 作者: 张磊
     * 日期: 16/12/6 上午11:54
     * 备注: template/统计分析/企业数据统计/信息量排行统计.ftl
     */
    public void list8() {
        Integer tjType = this.getIntParameter("tjType");
        if (tjType == null) {
            tjType = 100100;
        }
        Date startDate = this.getDateTimeParameter("startDate", DateFormatUtils.D_yyyyMMdd);
        if (startDate == null) {
            startDate = new Date(1000000000000L);
        }
        Date endDate = this.getDateTimeParameter("endDate", DateFormatUtils.D_yyyyMMdd);
        if (endDate == null) {
            endDate = new Date();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Collection<?> result = stsDataManager.findByHql("select sts.qy.jgmc,sts.org.orgName,sum(sts.sjl),sts.qy.id from NptStsData sts" +
                " where sts.stsTypeId=?" +
                " and sts.creditType=?" +
                " and sts.dataTime>=to_date(?,'yyyy-MM-dd')" +
                " and sts.dataTime<to_date(?,'yyyy-MM-dd')" +
                " group by (sts.qy.jgmc,sts.org.orgName,sts.qy.id)", 8L, tjType, sdf.format(startDate), sdf.format(endDate));
        this.outputSuccessResult(JSON.toJSONString(result));
    }

    public void detail8() {
        List<SimpleNode> result = new ArrayList<>();

        Integer tjType = this.getIntParameter("tjType");
        if (tjType == null) {
            tjType = 100100;
        }
        Long qyId = this.getLongParameter("qyId");

        // 设置开始日期 = 开始日期当月的第一天
        Date startDate = this.getDateTimeParameter("startDate", DateFormatUtils.D_yyyyMMdd);
        if (startDate == null) {
            startDate = new Date(1000000000000L);
        }
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        startCal.set(Calendar.DAY_OF_MONTH, 1);
        startCal.set(Calendar.HOUR_OF_DAY, 0);
        startCal.set(Calendar.MINUTE, 0);
        startCal.set(Calendar.SECOND, 0);
        startCal.set(Calendar.MILLISECOND, 0);


        // 设置结束日期 = 结束日期下个月的第一天
        Date endDate = this.getDateTimeParameter("endDate", DateFormatUtils.D_yyyyMMdd);
        if (endDate == null) {
            endDate = new Date();
        }
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        endCal.set(Calendar.MONTH, endCal.get(Calendar.MONTH) + 1);
        endCal.set(Calendar.DAY_OF_MONTH, 1);
        endCal.set(Calendar.HOUR_OF_DAY, 0);
        endCal.set(Calendar.MINUTE, 0);
        endCal.set(Calendar.SECOND, 0);
        endCal.set(Calendar.MILLISECOND, 0);

        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月");
        System.out.println(df.format(startCal.getTime()));
        System.out.println(df.format(endCal.getTime()));

        Date nextMonth;
        for (Date date = startCal.getTime(); date.compareTo(endCal.getTime()) < 0; ) {
            startCal.add(Calendar.MONTH, 1);
            nextMonth = startCal.getTime();

            Number sumByCondition = stsDataManager.getSumByCondition("sjl", Conditions.eq("stsTypeId", 8L), Conditions.eq("creditType", tjType), Conditions.eq("qy.id", qyId), Conditions.ge("dataTime", new java.sql.Timestamp(date.getTime())), Conditions.le("dataTime", new java.sql.Timestamp(nextMonth.getTime())));
            result.add(new SimpleNode(df.format(date), sumByCondition));

            date = nextMonth;
        }

        this.outputSuccessResult(JSON.toJSONString(result));
    }

    /**
     * 作者: 张磊
     * 日期: 16/12/5 下午3:32
     * 备注: template/统计分析/政府数据统计/人民群众反映部门意见情况统计.ftl
     */
    public void list9() {
        List<SimpleNode> result = new ArrayList<>();
        SimpleNode node;

        Date startDate = this.getDateTimeParameter("startDate", DateFormatUtils.D_yyyyMMdd);
        if (startDate == null) {
            startDate = new Date(1000000000000L);
        }
        Date endDate = this.getDateTimeParameter("endDate", DateFormatUtils.D_yyyyMMdd);
        if (endDate == null) {
            endDate = new Date();
        }

        NptLogicDataProvider rootOrg = rmsArchService.getRootOrg();
        if(rootOrg != null){
            Collection<NptLogicDataProvider> orgList = rmsArchService.listOrg(rootOrg.getId());

            for (NptLogicDataProvider org : orgList) {
                Number sumByCondition = stsDataManager.getSumByCondition("sjl", Conditions.eq("stsTypeId", 9L), Conditions.eq("org.id", org.getId()), Conditions.ge("dataTime", new java.sql.Timestamp(startDate.getTime())), Conditions.le("dataTime", new java.sql.Timestamp(endDate.getTime())));
                node = new SimpleNode(org.getId().toString(), org.getOrgName(), sumByCondition);
                result.add(node);
            }
        }

        Collections.sort(result);
        this.outputSuccessResult(JSON.toJSONString(result));
    }

    /**
     * 作者: 张磊
     * 日期: 16/12/5 下午3:13
     * 备注: template/统计分析/政府数据统计/单位行政效能考评结果详情.ftl
     */
    public void list10() {
        List<SimpleNode> result = new ArrayList<>();

        Long orgId = this.getLongParameter("orgId");

        // 设置开始日期 = 开始日期当月的第一天
        Date startDate = this.getDateTimeParameter("startDate", DateFormatUtils.D_yyyyMMdd);
        if (startDate == null) {
            startDate = new Date(1000000000000L);
        }
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        startCal.set(Calendar.DAY_OF_MONTH, 1);
        startCal.set(Calendar.HOUR_OF_DAY, 0);
        startCal.set(Calendar.MINUTE, 0);
        startCal.set(Calendar.SECOND, 0);
        startCal.set(Calendar.MILLISECOND, 0);


        // 设置结束日期 = 结束日期当月的第一天
        Date endDate = this.getDateTimeParameter("endDate", DateFormatUtils.D_yyyyMMdd);
        if (endDate == null) {
            endDate = new Date();
        }
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        endCal.set(Calendar.DAY_OF_MONTH, 1);
        endCal.set(Calendar.HOUR_OF_DAY, 0);
        endCal.set(Calendar.MINUTE, 0);
        endCal.set(Calendar.SECOND, 0);
        endCal.set(Calendar.MILLISECOND, 0);

        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月");
        System.out.println(df.format(startCal.getTime()));
        System.out.println(df.format(endCal.getTime()));

        Date nextMonth;
        List<Map> list=null;
        Object value=null;
        StringBuilder sb=new StringBuilder();
        for (Date date = startCal.getTime(); date.compareTo(endCal.getTime()) < 0; ) {
            startCal.add(Calendar.MONTH, 1);
            nextMonth = startCal.getTime();
            sb.append("select * from(select t.sjl from NPT_STS_DATA t left join NPT_ORGANIZATION d on t.org_id=d.id where t.sts_type_id=10");
            if(orgId!=-1){
                sb.append(" and t.org_id="+orgId+"");
            }
            sb.append(" and t.data_time>=to_timestamp('"+new java.sql.Timestamp(date.getTime())+"','YYYY-MM-DD HH24:MI:SS.FF')");
            sb.append(" and t.data_time<to_timestamp('"+new java.sql.Timestamp(nextMonth.getTime())+"','YYYY-MM-DD HH24:MI:SS.FF')");
            sb.append(" order by t.id desc) x where rownum=1");
            list=stsDataManager.selectUnitResult(sb.toString());
            if(list!=null&&list.size()>0){
                value=list.get(0).get("SJL");
                if (value!=null&&isNumeric(value.toString())){
                    result.add(new SimpleNode(df.format(date),Double.parseDouble(value.toString())));
                }else{
                    result.add(new SimpleNode(df.format(date),0));
                }
            }else{
                result.add(new SimpleNode(df.format(date),0));
            }
            date = nextMonth;
            sb.setLength(0);
        }

        this.outputSuccessResult(JSON.toJSONString(result));
    }

    /**
     * 作者: 张磊
     * 日期: 16/12/7 下午4:33
     * 备注: template/统计分析/政府数据统计/政府部门行政效能考评详情排名.ftl
     */
    public void list11() {
        Date startDate = this.getDateTimeParameter("startDate", DateFormatUtils.D_yyyyMMdd);
        if (startDate == null) {
            startDate = new Date(1000000000000L);
        }
        Date endDate = this.getDateTimeParameter("endDate", DateFormatUtils.D_yyyyMMdd);
        if (endDate == null) {
            endDate = new Date();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        Collection<?> result = stsDataManager.findByHql("select sts.org.orgName,to_char(sts.dataTime,'yyyy-MM'),sum(sts.sjl) from NptStsData sts" +
                " where sts.stsTypeId=?" +
                " and sts.dataTime>=to_date(?,'yyyy-MM')" +
                " and sts.dataTime<to_date(?,'yyyy-MM')" +
                " group by (sts.org.orgName,to_char(sts.dataTime,'yyyy-MM'))", 10L, sdf.format(startDate), sdf.format(endDate));
        this.outputSuccessResult(JSON.toJSONString(result));
    }

    /**
     * 作者: 张磊
     * 日期: 2017/04/05 下午02:57
     * 备注: 系统首页
     */
    public void nptIndex() {
        Map<String, Object> result = new HashMap<>();
        result.put("zsjl", stsDataManager.countZsjl());
        result.put("zzjgsl", stsDataManager.countZzjgsl());
        result.put("yhsl", stsDataManager.countYhsl());

        result.put("sjlzb", stsDataManager.groupSjlzb());
        result.put("xsjgsjl", stsDataManager.groupXsjgsjl());

        result.put("sjzzl", stsDataManager.countSjzzl());
        result.put("smyhzzl", stsDataManager.countSmyhzzl());

        this.outputSuccessResult(JSON.toJSONString(result));
    }

    /**
     * 作者: 张磊
     * 日期: 2017/04/12 下午08:38
     * 备注: 每月定时统计
     */
    @Scheduled(cron = "0 0 0 L * ?" )
    public void countData() {
        stsDataManager.save(new NptStsData((long) NptDict.STS_MONTH_DATA.getCode(), ((BigDecimal) stsDataManager.countZsjl()).longValue()));
        stsDataManager.save(new NptStsData((long) NptDict.STS_MONTH_CREDIT_USER.getCode(), ((BigDecimal) stsDataManager.countSmyh()).longValue()));
    }


    /**
     * 作者: 张磊
     * 日期: 2017/04/20 下午08:38
     * 备注: 组织机构管理页面统计
     */
    public void nptOrgIndex(){
        Map<String, Object> result = new HashMap<>();
        result.put("myzcsmyhs", stsDataManager.countMyzcsmyhs());
        result.put("mysjzzs", stsDataManager.countMysjzzs());

        result.put("gwbjsjl", stsDataManager.groupSjlzb());

        this.outputSuccessResult(JSON.toJSONString(result));
    }

    /***
      * 判断字符串是否为数字（包含小数点）
      * @param str
      * @return
      */
    public static boolean isNumeric(String str){
         Pattern pattern = Pattern.compile("[0-9]+.*[0-9]*");
         Matcher isNum = pattern.matcher(str);
         if( !isNum.matches() ){
           return false;
         }
         return true;
        }
}
