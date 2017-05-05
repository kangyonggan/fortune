package com.kangyonggan.app.fortune.biz.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Spring辅助类
 *
 * @author kangyonggan
 * @since 2016/12/6
 */
public final class SpringUtils implements ApplicationContextAware {
    private ApplicationContext context;

    private static SpringUtils INSTANCE;

    private SpringUtils() {
        INSTANCE = this;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static void autowire(Object bean) {
        INSTANCE.context.getAutowireCapableBeanFactory().autowireBean(bean);
    }

    public static Object getBean(String beanName) {
        return INSTANCE.context.getBean(beanName);
    }

    public static <T> T getBean(Class<T> clzz) {
        return INSTANCE.context.getBean(clzz);
    }
}
