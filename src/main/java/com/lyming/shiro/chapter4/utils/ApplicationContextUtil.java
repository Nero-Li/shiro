package com.lyming.shiro.chapter4.utils;

import javafx.application.Application;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Description :
 * @Author : Lyming
 * @Date: 2020-09-07 14:23
 */
@Component
public class ApplicationContextUtil implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    /**
     * 根据beanName获取bean
     * @param name
     * @return
     */
    public static Object getBean(String name) {
        return context.getBean(name);
    }
}
