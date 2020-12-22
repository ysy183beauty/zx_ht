package com.npt;

import com.npt.dict.NptDict;
import com.npt.model.bean.NptWebBridgeBean;
import com.npt.model.entity.NptBaseModel;
import com.npt.model.service.NptModelService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 项目：NPTWebApp
 * 作者：owen
 * 时间：2017/1/16 21:15
 * 描述:
 * <p>
 * 这是一个用于测试的Controller
 * <p>
 * 访问方式：ip:port/npt/test.jspx
 */
@Controller
@RequestMapping(value = "/npt", method = RequestMethod.GET)
public class NptTestAction implements InitializingBean {

    @Autowired
    private NptModelService service;

    public NptTestAction() {
        System.out.println("NptTestAction 已实例化.......");
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/16 下午2:9
     * 备注: getBaseModelGroupoolPaginationData
     */
    @RequestMapping("/test.jspx")
    public void test() {

        System.out.println("NptTestAction begin test()...................");

        NptWebBridgeBean bean = new NptWebBridgeBean();
        bean.setCurrPage(2);
        bean.setPageSize(12);
        Long poolId = 383L;

        NptDict result = service.getBaseModelGroupoolPaginationData(poolId,bean);

        System.out.println("NptTestAction end test()...................");
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/16 下午2:9
     * 备注: getModelMainFieldPaginationData
     */
    @RequestMapping("/test2.jspx")
    public void test2() {
        NptBaseModel model = service.findBaseModelById(345L);

        NptWebBridgeBean bean = new NptWebBridgeBean();

        NptDict result = service.getModelMainFieldPaginationData(model, bean);

        System.out.println(result.getCode());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("NptTestAction  已完成依赖实体的注入....");
    }
}
