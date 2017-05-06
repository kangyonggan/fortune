package com.kangyonggan.app.fortune.biz.service.impl;

import com.kangyonggan.app.fortune.biz.service.MerchantService;
import com.kangyonggan.app.fortune.model.annotation.CacheGetOrSave;
import com.kangyonggan.app.fortune.model.annotation.LogTime;
import com.kangyonggan.app.fortune.model.constants.AppConstants;
import com.kangyonggan.app.fortune.model.vo.Merchant;
import org.springframework.stereotype.Service;

/**
 * @author kangyonggan
 * @since 2017/5/4 0004
 */
@Service
public class MerchantServiceImpl extends BaseService<Merchant> implements MerchantService {

    @Override
    @LogTime
    @CacheGetOrSave("merchant:merchCo:{0}")
    public Merchant findMerchantByMerchCo(String merchCo) {
        Merchant merchant = new Merchant();
        merchant.setMerchCo(merchCo);
        merchant.setIsDeleted(AppConstants.IS_DELETED_NO);

        return myMapper.selectOne(merchant);
    }
}
