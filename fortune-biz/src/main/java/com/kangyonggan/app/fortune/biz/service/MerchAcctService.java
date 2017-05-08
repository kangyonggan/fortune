package com.kangyonggan.app.fortune.biz.service;

import com.kangyonggan.app.fortune.model.vo.MerchAcct;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2017/5/6 0006
 */
public interface MerchAcctService {

    /**
     * 根据商户号查找商户主卡信息
     *
     * @param merchCo
     * @return
     */
    MerchAcct findMerAcctByMerchNo(String merchCo);

    /**
     * 更新主卡余额
     *
     * @param ma
     */
    void updateMasterMerchAcct(MerchAcct ma);

    /**
     * 根据商户号和卡号查找商户卡信息
     *
     * @param merchCo
     * @param acctNo
     * @return
     */
    MerchAcct findMerAcctByMerchCoAndAcctNo(String merchCo, String acctNo);

    /**
     * 查询商户所有的卡
     *
     * @param merchCo
     * @return
     */
    List<MerchAcct> findMerAcctByMerchCo(String merchCo);

    /**
     * 保存银行卡
     *
     * @param merchAcct
     */
    void saveMerchAcct(MerchAcct merchAcct);

    /**
     * 更新银行卡信息
     *
     * @param merchAcct
     */
    void updateMerchAcct(MerchAcct merchAcct);

    /**
     * 删除
     *
     * @param merchAcct
     */
    void deleteMerchAcct(MerchAcct merchAcct);

    /**
     * 交易商户是否已绑此卡
     *
     * @param merchAcctNo
     * @return
     */
    boolean existsMerchAcctNo(String merchAcctNo);
}
