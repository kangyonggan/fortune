package com.kangyonggan.app.fortune.biz.service.impl;

import com.kangyonggan.app.fortune.biz.service.DictionaryService;
import com.kangyonggan.app.fortune.biz.service.TransService;
import com.kangyonggan.app.fortune.model.annotation.CacheDeleteAll;
import com.kangyonggan.app.fortune.model.annotation.CacheGetOrSave;
import com.kangyonggan.app.fortune.model.annotation.LogTime;
import com.kangyonggan.app.fortune.model.constants.AppConstants;
import com.kangyonggan.app.fortune.model.constants.DictionaryType;
import com.kangyonggan.app.fortune.model.vo.Dictionary;
import com.kangyonggan.app.fortune.model.vo.Trans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2017/5/4 0004
 */
@Service
public class TransServiceImpl extends BaseService<Trans> implements TransService {

    @Autowired
    private DictionaryService dictionaryService;

    @Override
    @LogTime
    @CacheGetOrSave("trans:merchCo:{0}:tranCo:{1}")
    public Trans findTransByMerchCoAndTranCo(String merchCo, String tranCo) {
        Trans trans = new Trans();
        trans.setMerchCo(merchCo);
        trans.setTranCo(tranCo);
        trans.setIsDeleted(AppConstants.IS_DELETED_NO);

        return myMapper.selectOne(trans);
    }

    @Override
    @LogTime
    public Trans findTransByMerchCoAndTranCoWithDeleted(String merchCo, String tranCo) {
        Trans trans = new Trans();
        trans.setMerchCo(merchCo);
        trans.setTranCo(tranCo);

        return myMapper.selectOne(trans);
    }

    @Override
    @LogTime
    public List<Trans> findTransByMerchCo(String merchCo) {
        Trans trans = new Trans();
        trans.setMerchCo(merchCo);

        return myMapper.select(trans);
    }

    @Override
    @LogTime
    @CacheDeleteAll("trans:merchCo:{0:merchCo}")
    public void saveTrans(Trans trans) {
        Dictionary dictionary = dictionaryService.findDictionaryByTypeAndCode(DictionaryType.TRANS_CO.getType(), trans.getTranCo());
        trans.setTranNm(dictionary.getValue());

        myMapper.insertSelective(trans);
    }

    @Override
    @LogTime
    @CacheDeleteAll("trans:merchCo:{0:merchCo}")
    public void updateTransByMerchCoAndTranCo(Trans trans) {
        Example example = new Example(Trans.class);
        example.createCriteria().andEqualTo("merchCo", trans.getMerchCo()).andEqualTo("tranCo", trans.getTranCo());

        myMapper.updateByExampleSelective(trans, example);
    }

    @Override
    @LogTime
    @CacheDeleteAll("trans:merchCo:{0:merchCo}")
    public void deleteTrans(Trans trans) {
        Example example = new Example(Trans.class);
        example.createCriteria().andEqualTo("merchCo", trans.getMerchCo()).andEqualTo("tranCo", trans.getTranCo());

        myMapper.deleteByExample(example);
    }

    @Override
    @LogTime
    public boolean exists(String merchCo, String tranCo) {
        Trans trans = new Trans();
        trans.setMerchCo(merchCo);
        trans.setTranCo(tranCo);

        return super.exists(trans);
    }
}
