package com.kangyonggan.app.fortune.biz.service;

import com.kangyonggan.app.fortune.model.vo.MerchAcct;

/**
 * @author kangyonggan
 * @since 2017/5/6 0006
 */
public interface MerchAcctService {

    /**
     * 根据商户号和卡号查找商户卡信息
     *
     * @param merchCo
     * @param acctNo
     * @return
     */
    MerchAcct findMerAcctByMerchNoAndAcctNo(String merchCo, String acctNo);

    /**
     * 更新余额
     *
     * @param ma
     */
    void updateMerchAcct(MerchAcct ma);
}
