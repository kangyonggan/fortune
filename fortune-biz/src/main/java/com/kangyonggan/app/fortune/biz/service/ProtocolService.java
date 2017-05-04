package com.kangyonggan.app.fortune.biz.service;

import com.kangyonggan.app.fortune.model.vo.Protocol;

/**
 * @author kangyonggan
 * @since 2017/5/4 0004
 */
public interface ProtocolService {

    /**
     * 查找签约协议
     *
     * @param merchCo
     * @param acctNo
     * @return
     */
    Protocol findProtocolByMerchCoAndAcctNo(String merchCo, String acctNo);

}
