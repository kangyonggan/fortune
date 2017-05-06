package com.kangyonggan.app.fortune.biz.service.impl;

import com.kangyonggan.app.fortune.biz.service.ProtocolService;
import com.kangyonggan.app.fortune.mapper.ProtocolMapper;
import com.kangyonggan.app.fortune.model.annotation.CacheDelete;
import com.kangyonggan.app.fortune.model.annotation.CacheGetOrSave;
import com.kangyonggan.app.fortune.model.annotation.LogTime;
import com.kangyonggan.app.fortune.model.constants.AppConstants;
import com.kangyonggan.app.fortune.model.vo.Protocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * @author kangyonggan
 * @since 2017/5/4 0004
 */
@Service
public class ProtocolServiceImpl extends BaseService<Protocol> implements ProtocolService {

    @Autowired
    private ProtocolMapper protocolMapper;

    @Override
    @LogTime
    public Protocol findProtocolByMerchCoAndAcctNo(String merchCo, String acctNo) {
        Protocol protocol = new Protocol();
        protocol.setMerchCo(merchCo);
        protocol.setAcctNo(acctNo);
        protocol.setIsDeleted(AppConstants.IS_DELETED_NO);

        return super.selectOne(protocol);
    }

    @Override
    @LogTime
    @CacheDelete("protocol:protocolNo:{0:protocolNo}")
    public void updateProtocolByProtocolNo(Protocol protocol) {
        Example example = new Example(Protocol.class);
        example.createCriteria().andEqualTo("protocolNo", protocol.getProtocolNo());

        protocolMapper.updateByExampleSelective(protocol, example);
    }

    @Override
    @LogTime
    public void saveProtocol(Protocol protocol) {
        super.insertSelective(protocol);
    }

    @Override
    @LogTime
    @CacheGetOrSave("protocol:protocolNo:{0}")
    public Protocol findProtocolByProtocolNo(String protocolNo) {
        Protocol protocol = new Protocol();
        protocol.setProtocolNo(protocolNo);
        protocol.setIsDeleted(AppConstants.IS_DELETED_NO);

        return super.selectOne(protocol);
    }
}
