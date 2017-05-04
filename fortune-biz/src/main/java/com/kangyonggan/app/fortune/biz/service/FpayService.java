package com.kangyonggan.app.fortune.biz.service;

import com.kangyonggan.app.fortune.common.exception.ParseException;

/**
 * 发财付业务核心处理器
 *
 * @author kangyonggan
 * @since 5/4/17
 */
public interface FpayService {

    /**
     * 解析报文、分发请求、构建报文
     *
     * @param reqXml 请求报文
     * @return
     * @throws ParseException 解析异常
     * @throws Exception      未知异常
     */
    String execute(String reqXml) throws ParseException, Exception;
}
