package com.kangyonggan.app.fortune.biz.service;

import com.kangyonggan.app.fortune.model.vo.Trans;

/**
 * @author kangyonggan
 * @since 2017/5/4 0004
 */
public interface TransService {

    /**
     * 查询商户的交易类型
     *
     * @param merchCo
     * @param tranCo
     * @return
     */
    Trans findTransByMerchCoAndTranCo(String merchCo, String tranCo);

}
