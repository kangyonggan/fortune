package com.kangyonggan.app.fortune.biz.service.impl;

import com.kangyonggan.app.fortune.biz.service.MerchAcctService;
import com.kangyonggan.app.fortune.model.annotation.LogTime;
import com.kangyonggan.app.fortune.model.constants.AppConstants;
import com.kangyonggan.app.fortune.model.vo.MerchAcct;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * @author kangyonggan
 * @since 2017/5/6 0006
 */
@Service
public class MerchAcctServiceImpl extends BaseService<MerchAcct> implements MerchAcctService {

    @Override
    @LogTime
    public MerchAcct findMerAcctByMerchNo(String merchCo) {
        MerchAcct merchAcct = new MerchAcct();
        merchAcct.setMerchCo(merchCo);
        merchAcct.setIsMaster((byte) 1);
        merchAcct.setIsDeleted(AppConstants.IS_DELETED_NO);

        return myMapper.selectOne(merchAcct);
    }

    @Override
    @LogTime
    public void updateMerchAcct(MerchAcct ma) {
        Example example = new Example(MerchAcct.class);
        example.createCriteria().andEqualTo("merchCo", ma.getMerchCo()).andEqualTo("isMaster", 1);

        myMapper.updateByExampleSelective(ma, example);
    }

    @Override
    @LogTime
    public MerchAcct findMerAcctByMerchNoAndAcctNo(String merchCo, String acctNo) {
        MerchAcct merchAcct = new MerchAcct();
        merchAcct.setMerchCo(merchCo);
        merchAcct.setMerchAcctNo(acctNo);
        merchAcct.setIsDeleted(AppConstants.IS_DELETED_NO);

        return myMapper.selectOne(merchAcct);
    }
}
