package com.kangyonggan.app.fortune.biz.service.impl;

import com.kangyonggan.app.fortune.biz.service.*;
import com.kangyonggan.app.fortune.model.xml.Fpay;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author kangyonggan
 * @since 5/4/17
 */
@Service
@Log4j2
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class FpayServiceImpl implements FpayService {

    @Autowired
    private ProtocolService protocolService;

    @Autowired
    private CommandService commandService;

    @Autowired
    private FpayHelper fpayHelper;

    @Autowired
    private MerchAcctService merchAcctService;

    @Override
    public void sign(Fpay fpay) throws Exception {

    }

    @Override
    public void unsign(Fpay fpay) throws Exception {

    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void pay(Fpay fpay) throws Exception {

    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void redeem(Fpay fpay) throws Exception {

    }

    @Override
    public void query(Fpay fpay) throws Exception {

    }

    @Override
    public void queryBalance(Fpay fpay) throws Exception {

    }
}
