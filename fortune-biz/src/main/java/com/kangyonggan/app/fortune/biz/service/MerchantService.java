package com.kangyonggan.app.fortune.biz.service;

import com.kangyonggan.app.fortune.model.vo.Merchant;

/**
 * @author kangyonggan
 * @since 2017/5/4 0004
 */
public interface MerchantService {

    /**
     * 查找商户信息
     *
     * @param merchCo 商户号
     * @return
     */
    Merchant findMerchantByMerchCo(String merchCo);

}
