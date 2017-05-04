package com.kangyonggan.app.fortune.biz.service.impl;

import com.kangyonggan.app.fortune.biz.service.ProtocolService;
import com.kangyonggan.app.fortune.model.annotation.LogTime;
import com.kangyonggan.app.fortune.model.constants.AppConstants;
import com.kangyonggan.app.fortune.model.vo.Protocol;
import org.springframework.stereotype.Service;

/**
 * @author kangyonggan
 * @since 2017/5/4 0004
 */
@Service
public class ProtocolServiceImpl extends BaseService<Protocol> implements ProtocolService {

    @Override
    @LogTime
    public Protocol findProtocolByMerchCoAndAcctNo(String merchCo, String acctNo) {
        Protocol protocol = new Protocol();
        protocol.setMerchCo(merchCo);
        protocol.setAcctNo(acctNo);
        protocol.setIsDeleted(AppConstants.IS_DELETED_NO);

        return super.selectOne(protocol);
    }
}
