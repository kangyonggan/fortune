package com.kangyonggan.app.fortune.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.kangyonggan.app.fortune.biz.service.MerchantService;
import com.kangyonggan.app.fortune.biz.service.ProtocolService;
import com.kangyonggan.app.fortune.common.util.DateUtil;
import com.kangyonggan.app.fortune.model.annotation.CacheDelete;
import com.kangyonggan.app.fortune.model.annotation.CacheGetOrSave;
import com.kangyonggan.app.fortune.model.annotation.LogTime;
import com.kangyonggan.app.fortune.model.constants.AppConstants;
import com.kangyonggan.app.fortune.model.vo.Protocol;
import com.kangyonggan.app.fortune.model.vo.ShiroMerchant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.util.List;

/**
 * @author kangyonggan
 * @since 2017/5/4 0004
 */
@Service
public class ProtocolServiceImpl extends BaseService<Protocol> implements ProtocolService {

    @Autowired
    private MerchantService merchantService;

    @Override
    @LogTime
    public Protocol findProtocolByMerchCoAndAcctNo(String merchCo, String acctNo) {
        Protocol protocol = new Protocol();
        protocol.setMerchCo(merchCo);
        protocol.setAcctNo(acctNo);
        protocol.setIsDeleted(AppConstants.IS_DELETED_NO);

        return myMapper.selectOne(protocol);
    }

    @Override
    @LogTime
    @CacheDelete("protocol:protocolNo:{0:protocolNo}")
    public void updateProtocolByProtocolNo(Protocol protocol) {
        Example example = new Example(Protocol.class);
        example.createCriteria().andEqualTo("protocolNo", protocol.getProtocolNo());

        myMapper.updateByExampleSelective(protocol, example);
    }

    @Override
    @LogTime
    public void saveProtocol(Protocol protocol) {
        myMapper.insertSelective(protocol);
    }

    @Override
    @LogTime
    @CacheGetOrSave("protocol:protocolNo:{0}")
    public Protocol findProtocolByProtocolNo(String protocolNo) {
        Protocol protocol = new Protocol();
        protocol.setProtocolNo(protocolNo);
        protocol.setIsDeleted(AppConstants.IS_DELETED_NO);

        return myMapper.selectOne(protocol);
    }

    @Override
    @LogTime
    public List<Protocol> searchProtocols(int pageNum, String startDate, String endDate, String protocolNo, String acctNo) throws ParseException {
        Example example = new Example(Protocol.class);
        Example.Criteria criteria = example.createCriteria();
        ShiroMerchant shiroMerchant = merchantService.getShiroMerchant();

        criteria.andEqualTo("merchCo", shiroMerchant.getMerchCo());
        if (StringUtils.isNotEmpty(startDate)) {
            criteria.andGreaterThanOrEqualTo("createdTime", DateUtil.fromDate(startDate));
        }
        if (StringUtils.isNotEmpty(endDate)) {
            criteria.andLessThanOrEqualTo("createdTime", DateUtil.fromDate(endDate));
        }
        if (StringUtils.isNotEmpty(protocolNo)) {
            criteria.andEqualTo("protocolNo", protocolNo);
        }
        if (StringUtils.isNotEmpty(acctNo)) {
            criteria.andEqualTo("acctNo", acctNo);
        }


        PageHelper.startPage(pageNum, AppConstants.PAGE_SIZE);
        return myMapper.selectByExample(example);
    }
}
