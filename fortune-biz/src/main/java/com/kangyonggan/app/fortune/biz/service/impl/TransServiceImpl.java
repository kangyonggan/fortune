package com.kangyonggan.app.fortune.biz.service.impl;

import com.kangyonggan.app.fortune.biz.service.TransService;
import com.kangyonggan.app.fortune.model.annotation.CacheGetOrSave;
import com.kangyonggan.app.fortune.model.annotation.LogTime;
import com.kangyonggan.app.fortune.model.constants.AppConstants;
import com.kangyonggan.app.fortune.model.vo.Trans;
import org.springframework.stereotype.Service;

/**
 * @author kangyonggan
 * @since 2017/5/4 0004
 */
@Service
public class TransServiceImpl extends BaseService<Trans> implements TransService {

    @Override
    @LogTime
    @CacheGetOrSave("trans:merchCo:{0}:tranCo:{1}")
    public Trans findTransByMerchCoAndTranCo(String merchCo, String tranCo) {
        Trans trans = new Trans();
        trans.setMerchCo(merchCo);
        trans.setTranCo(tranCo);
        trans.setIsDeleted(AppConstants.IS_DELETED_NO);

        return super.selectOne(trans);
    }
}
