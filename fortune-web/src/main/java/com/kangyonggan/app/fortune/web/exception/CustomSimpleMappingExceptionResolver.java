package com.kangyonggan.app.fortune.web.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author kangyonggan
 * @since 16/9/25
 */
@Log4j2
public class CustomSimpleMappingExceptionResolver extends SimpleMappingExceptionResolver {

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        log.warn("控制器异常", ex);
        String viewName = determineViewName(ex, request);
        if (viewName != null) {
            if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request
                    .getHeader("X-Requested-With") != null && request
                    .getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
                // 如果不是异步请求
                Integer statusCode = determineStatusCode(request, viewName);
                if (statusCode != null) {
                    applyStatusCodeIfPossible(request, response, statusCode);
                }
                return getModelAndView(viewName, ex, request);
            } else {
                try {
                    PrintWriter writer = response.getWriter();
                    writer.write(ex.getMessage());
                    writer.flush();
                } catch (IOException e) {
                    logger.warn("出错了!", e);
                }
                return null;

            }
        } else {
            return new ModelAndView("500");
        }
    }
}
