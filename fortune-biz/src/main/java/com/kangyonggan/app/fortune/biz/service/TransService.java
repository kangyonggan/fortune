package com.kangyonggan.app.fortune.biz.service;

import com.kangyonggan.app.fortune.model.vo.Trans;

import java.util.List;

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

    /**
     * 查找商户所有交易类
     *
     * @param merchCo
     * @return
     */
    List<Trans> findTransByMerchCo(String merchCo);

    /**
     * 保存交易类型
     *
     * @param trans
     */
    void saveTrans(Trans trans);

    /**
     * 更新交易类型
     *
     * @param trans
     */
    void updateTransByMerchCoAndTranCo(Trans trans);

    /**
     * 删除
     *
     * @param trans
     */
    void deleteTrans(Trans trans);
}
