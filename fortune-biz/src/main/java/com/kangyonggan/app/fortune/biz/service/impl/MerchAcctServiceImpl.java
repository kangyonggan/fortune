package com.kangyonggan.app.fortune.biz.service.impl;

import com.kangyonggan.app.fortune.biz.service.MerchAcctService;
import com.kangyonggan.app.fortune.biz.service.MerchantService;
import com.kangyonggan.app.fortune.model.annotation.LogTime;
import com.kangyonggan.app.fortune.model.constants.AppConstants;
import com.kangyonggan.app.fortune.model.vo.MerchAcct;
import com.kangyonggan.app.fortune.model.vo.ShiroMerchant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2017/5/6 0006
 */
@Service
public class MerchAcctServiceImpl extends BaseService<MerchAcct> implements MerchAcctService {

    @Autowired
    private MerchantService merchantService;

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
    public void updateMasterMerchAcct(MerchAcct ma) {
        Example example = new Example(MerchAcct.class);
        example.createCriteria().andEqualTo("merchCo", ma.getMerchCo()).andEqualTo("isMaster", 1);

        myMapper.updateByExampleSelective(ma, example);
    }

    @Override
    @LogTime
    public MerchAcct findMerAcctByMerchCoAndAcctNo(String merchCo, String acctNo) {
        MerchAcct merchAcct = new MerchAcct();
        merchAcct.setMerchCo(merchCo);
        merchAcct.setMerchAcctNo(acctNo);
        merchAcct.setIsDeleted(AppConstants.IS_DELETED_NO);

        return myMapper.selectOne(merchAcct);
    }

    @Override
    @LogTime
    public List<MerchAcct> findMerAcctByMerchCo(String merchCo) {
        MerchAcct merchAcct = new MerchAcct();
        merchAcct.setMerchCo(merchCo);
        merchAcct.setIsDeleted(AppConstants.IS_DELETED_NO);

        return myMapper.select(merchAcct);
    }

    @Override
    @LogTime
    public void saveMerchAcct(MerchAcct merchAcct) {
        if (merchAcct.getIsMaster() == 1) {
            updateMaster(merchAcct);
        }

        myMapper.insertSelective(merchAcct);
    }

    @Override
    @LogTime
    public void updateMerchAcct(MerchAcct merchAcct) {
        if (merchAcct.getIsMaster() == 1) {
            updateMaster(merchAcct);
        }

        myMapper.updateByPrimaryKeySelective(merchAcct);
    }

    private void updateMaster(MerchAcct merchAcct) {
        MerchAcct ma = new MerchAcct();
        ma.setIsMaster((byte) 0);

        Example example = new Example(MerchAcct.class);
        example.createCriteria().andEqualTo("merchCo", merchAcct.getMerchCo());

        myMapper.updateByExampleSelective(ma, example);
    }

    @Override
    @LogTime
    public void deleteMerchAcct(MerchAcct merchAcct) {
        myMapper.delete(merchAcct);
    }

    @Override
    @LogTime
    public boolean existsMerchAcctNo(String merchAcctNo) {
        ShiroMerchant merchant = merchantService.getShiroMerchant();
        MerchAcct merchAcct = new MerchAcct();
        merchAcct.setMerchAcctNo(merchAcctNo);
        merchAcct.setMerchCo(merchant.getMerchCo());

        return super.exists(merchAcct);
    }
}
