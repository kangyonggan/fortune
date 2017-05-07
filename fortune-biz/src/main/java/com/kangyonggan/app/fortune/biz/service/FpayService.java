package com.kangyonggan.app.fortune.biz.service;

import com.kangyonggan.app.fortune.common.exception.EmptyParamsException;
import com.kangyonggan.app.fortune.common.exception.ValidParamsException;
import com.kangyonggan.app.fortune.model.xml.Fpay;

/**
 * 发财付业务核心接口
 *
 * @author kangyonggan
 * @since 5/4/17
 */
public interface FpayService {

    /**
     * 签约
     *
     * @param merchCo 商户号
     * @param fpay 请求
     * @throws EmptyParamsException 必填域缺失
     * @throws ValidParamsException 数据不合法
     * @throws Exception            未知异常
     */
    void sign(String merchCo, Fpay fpay) throws EmptyParamsException, ValidParamsException, Exception;

    /**
     * 解约
     *
     * @param merchCo 商户号
     * @param fpay 请求
     * @throws EmptyParamsException 必填域缺失
     * @throws ValidParamsException 数据不合法
     * @throws Exception            未知异常
     */
    void unsign(String merchCo, Fpay fpay) throws EmptyParamsException, ValidParamsException, Exception;

    /**
     * 单笔代扣
     *
     * @param merchCo 商户号
     * @param fpay 请求
     * @throws EmptyParamsException 必填域缺失
     * @throws ValidParamsException 数据不合法
     * @throws Exception            未知异常
     */
    void pay(String merchCo, Fpay fpay) throws EmptyParamsException, ValidParamsException, Exception;

    /**
     * 单笔代付
     *
     * @param merchCo 商户号
     * @param fpay 请求
     * @throws EmptyParamsException 必填域缺失
     * @throws ValidParamsException 数据不合法
     * @throws Exception            未知异常
     */
    void redeem(String merchCo, Fpay fpay) throws EmptyParamsException, ValidParamsException, Exception;

    /**
     * 交易查询
     *
     * @param merchCo 商户号
     * @param fpay 请求
     * @throws EmptyParamsException 必填域缺失
     * @throws ValidParamsException 数据不合法
     * @throws Exception            未知异常
     */
    void query(String merchCo, Fpay fpay) throws EmptyParamsException, ValidParamsException, Exception;

    /**
     * 余额查询
     *
     * @param merchCo 商户号
     * @param fpay 请求
     * @throws EmptyParamsException 必填域缺失
     * @throws ValidParamsException 数据不合法
     * @throws Exception            未知异常
     */
    void queryBalance(String merchCo, Fpay fpay) throws EmptyParamsException, ValidParamsException, Exception;
}
