package com.kangyonggan.app.fortune.biz.asm;

import com.kangyonggan.app.fortune.model.annotation.LogTime;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author kangyonggan
 * @since 5/19/17
 */
@Log4j2
//@Component
public class MyConfigImpl implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        try {
            return processBefore(bean);
        } catch (Exception e) {
            log.error("处理前置增强异常", e);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * 处理前置增强
     *
     * @param bean
     * @throws Exception
     */
    private Object processBefore(Object bean) throws Exception {
        Method methods[] = bean.getClass().getDeclaredMethods();
        for (Method method : methods) {
            Annotation anno = method.getAnnotation(LogTime.class);
            if (anno != null) {
                // TODO
            }
        }

        return bean;
    }

}
