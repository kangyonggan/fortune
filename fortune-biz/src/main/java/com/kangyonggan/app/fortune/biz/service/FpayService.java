package com.kangyonggan.app.fortune.biz.service;

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
     * @param fpay 请求
     * @throws Exception 未知异常
     */
    void sign(Fpay fpay) throws Exception;

    /**
     * 解约
     *
     * @param fpay 请求
     * @throws Exception 未知异常
     */
    void unsign(Fpay fpay) throws Exception;

    /**
     * 单笔代扣
     *
     * @param fpay 请求
     * @throws Exception 未知异常
     */
    void pay(Fpay fpay) throws Exception;

    /**
     * 单笔代付
     *
     * @param fpay 请求
     * @throws Exception 未知异常
     */
    void redeem(Fpay fpay) throws Exception;

    /**
     * 交易查询
     *
     * @param fpay 请求
     * @throws Exception 未知异常
     */
    void query(Fpay fpay) throws Exception;

    /**
     * 余额查询
     *
     * @param fpay 请求
     * @throws Exception 未知异常
     */
    void queryBalance(Fpay fpay) throws Exception;

}
