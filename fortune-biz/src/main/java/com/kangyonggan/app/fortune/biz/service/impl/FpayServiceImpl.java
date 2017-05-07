package com.kangyonggan.app.fortune.biz.service.impl;

import com.kangyonggan.app.fortune.biz.service.FpayHelper;
import com.kangyonggan.app.fortune.biz.service.FpayService;
import com.kangyonggan.app.fortune.biz.service.ProtocolService;
import com.kangyonggan.app.fortune.common.exception.EmptyParamsException;
import com.kangyonggan.app.fortune.common.exception.ValidParamsException;
import com.kangyonggan.app.fortune.common.util.DateUtil;
import com.kangyonggan.app.fortune.model.constants.Resp;
import com.kangyonggan.app.fortune.model.vo.Protocol;
import com.kangyonggan.app.fortune.model.xml.Fpay;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.beanutils.PropertyUtils;
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
    private FpayHelper fpayHelper;

    @Override
    public void sign(String merchCo, Fpay fpay) throws EmptyParamsException, ValidParamsException, Exception {
        log.info("==================== 进入发财付签约接口 ====================");
        // 必填域校验
        FpayHelper.checkSignEmpty(fpay);

        // 合法性校验
        FpayHelper.checkSignValid(fpay);

        // 是否第一次签约
        Protocol protocol = protocolService.findProtocolByMerchCoAndAcctNo(merchCo, fpay.getAcctNo());
        String protocolNo;
        if (protocol == null) {
            log.info("第一次签约");
            protocol = new Protocol();
            PropertyUtils.copyProperties(protocol, fpay);

            protocolNo = fpayHelper.genProtocolNo();
            protocol.setProtocolNo(protocolNo);
            protocol.setMerchCo(merchCo);
            protocol.setExpiredTime(DateUtil.plusYears(10));// 10年协议有效期

            protocolService.saveProtocol(protocol);
            log.info("新协议保存成功");
        } else {
            log.info("重复签约");
            protocolNo = protocol.getProtocolNo();
            protocol = new Protocol();
            protocol.setProtocolNo(protocolNo);
            protocol.setIsUnsign((byte) 0);
            protocol.setExpiredTime(DateUtil.plusYears(10));// 10年协议有效期

            protocolService.updateProtocolByProtocolNo(protocol);
            log.info("协议修改成功");
        }

        // 回写响应数据
        Resp resp = Resp.RESP_CO_0000;
        writeCommonResp(fpay, resp);
        fpay.setProtocolNo(protocolNo);

        log.info("==================== 离开发财付签约接口 ====================");
    }

    @Override
    public void unsign(String merchCo, Fpay fpay) throws EmptyParamsException, ValidParamsException, Exception {
        log.info("==================== 进入发财付解约接口 ====================");
        // 必填域校验
        FpayHelper.checkSignEmpty(fpay);

        // 合法性校验
        FpayHelper.checkSignValid(fpay);

        Resp resp = Resp.RESP_CO_0000;

        // 是否有签约记录
        Protocol protocol = protocolService.findProtocolByMerchCoAndAcctNo(merchCo, fpay.getAcctNo());
        String protocolNo = null;
        if (protocol == null) {
            resp = Resp.RESP_CO_0010;
        } else {
            protocolNo = protocol.getProtocolNo();
            protocol = new Protocol();
            protocol.setProtocolNo(protocolNo);
            protocol.setIsUnsign((byte) 1);

            protocolService.updateProtocolByProtocolNo(protocol);
            log.info("解约成功");
        }

        // 回写响应数据
        writeCommonResp(fpay, resp);
        fpay.setProtocolNo(protocolNo);

        log.info("==================== 离开发财付解约接口 ====================");
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void pay(String merchCo, Fpay fpay) throws EmptyParamsException, ValidParamsException, Exception {

    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void redeem(String merchCo, Fpay fpay) throws EmptyParamsException, ValidParamsException, Exception {

    }

    @Override
    public void query(String merchCo, Fpay fpay) throws EmptyParamsException, ValidParamsException, Exception {

    }

    @Override
    public void queryBalance(String merchCo, Fpay fpay) throws EmptyParamsException, ValidParamsException, Exception {

    }

    /**
     * 回写公共响应字段
     *
     * @param fpay
     * @param resp
     */
    private void writeCommonResp(Fpay fpay, Resp resp) {
        fpay.setRespCo(resp.getRespCo());
        fpay.setRespMsg(resp.getRespMsg());
        fpay.setFpayDate(DateUtil.getDate());
        fpay.setFpaySerialNo(fpayHelper.genSerialNo());
    }
}
