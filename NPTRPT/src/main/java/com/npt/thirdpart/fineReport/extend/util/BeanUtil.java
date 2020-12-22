package com.npt.thirdpart.fineReport.extend.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class BeanUtil implements ApplicationContextAware {
    private static ApplicationContext staticContext;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        staticContext = context;
    }

    public static Object getBean(String strBeanName) {
        return staticContext.getBean(strBeanName);
    }

    public static <T> T getBean(Class<T> requiredType) {
        return staticContext.getBean(requiredType);
    }
}
