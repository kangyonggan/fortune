package com.kangyonggan.app.fortune.biz.aspect;


import com.kangyonggan.app.fortune.biz.util.PropertiesUtil;
import com.kangyonggan.app.fortune.common.util.AspectUtil;
import com.kangyonggan.app.fortune.model.annotation.LogTime;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 切于内部service的实现方法上， 需要在方法上手动加上@LogTime注解， 打印入参和出参，打印方法执行时间, 慢方法打印error日志
 *
 * @author kangyonggan
 * @since 2016/11/30
 */
@Log4j2
@Aspect
@Component
public class LogAspect {

    /**
     * 设定的方法最大执行时间
     */
    private Long slowMethodTime;

    public LogAspect() {
        String val = PropertiesUtil.getPropertiesWithDefault("slow.method.time", "2");
        slowMethodTime = Long.parseLong(val);
    }

    @Pointcut("execution(* com.kangyonggan.app.fortune.biz..*.*(..))")
    public void pointcut() {
    }

    /**
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object args[] = joinPoint.getArgs();
        Class clazz = joinPoint.getTarget().getClass();

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = clazz.getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
        String targetName = "[" + clazz.getName() + "." + method.getName() + "]";

        LogTime logTime = method.getAnnotation(LogTime.class);
        Object result;
        if (logTime != null) {
            log.info("进入方法:" + targetName + " - args:" + AspectUtil.getStringFromRequest(args));

            long beginTime = System.currentTimeMillis();
            result = joinPoint.proceed(args);
            long endTime = System.currentTimeMillis();
            long time = endTime - beginTime;

            log.info("离开方法:" + targetName + " - return:" + AspectUtil.getStringFromResponse(result));
            log.info("方法耗时:" + time + "ms - " + targetName);

            if (time > slowMethodTime * 1000) {
                log.warn("方法执行超过设定时间" + slowMethodTime + "s," + targetName);
            }
        } else {
            result = joinPoint.proceed(args);
        }

        return result;
    }
}
