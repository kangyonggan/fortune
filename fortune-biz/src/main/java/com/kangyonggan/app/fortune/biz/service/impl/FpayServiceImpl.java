package com.kangyonggan.app.fortune.biz.service.impl;

import com.kangyonggan.app.fortune.biz.service.*;
import com.kangyonggan.app.fortune.common.exception.EmptyParamsException;
import com.kangyonggan.app.fortune.common.exception.ValidParamsException;
import com.kangyonggan.app.fortune.common.util.DateUtil;
import com.kangyonggan.app.fortune.model.constants.Resp;
import com.kangyonggan.app.fortune.model.constants.TranCo;
import com.kangyonggan.app.fortune.model.constants.TranSt;
import com.kangyonggan.app.fortune.model.vo.Command;
import com.kangyonggan.app.fortune.model.vo.MerchAcct;
import com.kangyonggan.app.fortune.model.vo.Protocol;
import com.kangyonggan.app.fortune.model.xml.Fpay;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
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

    @Autowired
    private CommandService commandService;

    @Autowired
    private MerchAcctService merchAcctService;

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
        log.info("==================== 进入发财付单笔代扣接口 ====================");
        // 必填域校验
        FpayHelper.checkPayEmpty(fpay);

        // 合法性校验
        FpayHelper.checkPayValid(fpay);

        Resp resp;

        // 协议是否有效
        Protocol protocol = protocolService.findProtocolByProtocolNo(fpay.getProtocolNo());
        if (protocol == null || protocol.getIsUnsign() == 1 || protocol.getExpiredTime().before(DateUtil.now())) {
            resp = Resp.RESP_CO_0012;
        } else {
            // 更新交易状态, 交易金额后两位前补00即响应码，没对应的响应码则为成功。
            String amount = fpay.getAmount().toString();
            log.info("交易金额为：{}", amount);

            MerchAcct merchAcct = merchAcctService.findMerAcctByMerchNo(merchCo);
            if (merchAcct == null) {
                resp = Resp.RESP_CO_0013;
                log.info(resp.getRespMsg());
            } else if (fpay.getAmount().compareTo(merchAcct.getBalance()) > 0) {
                resp = Resp.RESP_CO_0011;
                log.info(resp.getRespMsg());
            } else {
                int index = amount.lastIndexOf(".");
                String end = "0000";
                if (index != -1) {
                    end = "00" + amount.substring(index + 1);
                    end = StringUtils.rightPad(end, 4, "0");
                }
                resp = Resp.getRespCo(end);

                if ("Y,E,I".indexOf(resp.getTranSt()) > -1) {
                    // 注定成功的交易才扣头寸
                    MerchAcct ma = new MerchAcct();
                    ma.setMerchCo(merchCo);
                    ma.setBalance(merchAcct.getBalance().subtract(fpay.getAmount()));

                    merchAcctService.updateMasterMerchAcct(ma);
                    log.info("商户头寸已扣除");
                }
            }
        }

        // 落库
        writeCommon(merchCo, TranCo.K003.name(), resp.getTranSt(), fpay);
        log.info("交易落库成功");

        // 回写响应数据
        writeCommonResp(fpay, resp);

        log.info("==================== 离开发财付单笔代扣接口 ====================");
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void redeem(String merchCo, Fpay fpay) throws EmptyParamsException, ValidParamsException, Exception {
        log.info("==================== 进入发财付单笔代付接口 ====================");
        // 必填域校验
        FpayHelper.checkPayEmpty(fpay);

        // 合法性校验
        FpayHelper.checkPayValid(fpay);

        Resp resp;

        // 协议是否有效
        Protocol protocol = protocolService.findProtocolByProtocolNo(fpay.getProtocolNo());
        if (protocol == null || protocol.getIsUnsign() == 1 || protocol.getExpiredTime().before(DateUtil.now())) {
            resp = Resp.RESP_CO_0012;
        } else {
            // 更新交易状态, 交易金额后两位前补00即响应码，没对应的响应码则为成功。
            String amount = fpay.getAmount().toString();
            log.info("交易金额为：{}", amount);

            MerchAcct merchAcct = merchAcctService.findMerAcctByMerchNo(merchCo);
            if (merchAcct == null) {
                resp = Resp.RESP_CO_0013;
                log.info(resp.getRespMsg());
            } else {
                int index = amount.lastIndexOf(".");
                String end = "0000";
                if (index != -1) {
                    end = "00" + amount.substring(index + 1);
                    end = StringUtils.rightPad(end, 4, "0");
                }
                resp = Resp.getRespCo(end);

                if ("Y,E,I".indexOf(resp.getTranSt()) > -1) {
                    MerchAcct ma = new MerchAcct();
                    ma.setMerchCo(merchCo);
                    ma.setBalance(merchAcct.getBalance().add(fpay.getAmount()));

                    merchAcctService.updateMasterMerchAcct(ma);
                    log.info("商户头寸已增加");
                }
            }
        }

        // 落库
        writeCommon(merchCo, TranCo.K004.name(), resp.getTranSt(), fpay);
        log.info("交易落库成功");

        // 回写响应数据
        writeCommonResp(fpay, resp);

        log.info("==================== 离开发财付单笔代付接口 ====================");
    }

    @Override
    public void query(String merchCo, Fpay fpay) throws EmptyParamsException, ValidParamsException, Exception {
        log.info("==================== 进入发财付交易查询接口 ====================");
        // 必填域校验
        FpayHelper.checkQueryEmpty(fpay);

        // 合法性校验
        FpayHelper.checkQueryValid(fpay);

        Resp resp = Resp.RESP_CO_0000;

        // 查询交易
        Command command = commandService.findCommandBySerialNo(fpay.getOrgnSerialNo());
        if (command == null) {
            fpay.setTranSt(TranSt.F.name());
        } else {
            fpay.setTranSt(command.getTranSt());
            fpay.setReqDate(command.getReqDate());
            fpay.setReqTime(command.getReqTime());
        }

        // 回写响应数据
        fpay.setRespCo(resp.getRespCo());
        fpay.setRespMsg(resp.getRespMsg());

        log.info("==================== 离开发财付交易查询接口 ====================");
    }

    @Override
    public void queryBalance(String merchCo, Fpay fpay) throws EmptyParamsException, ValidParamsException, Exception {
        log.info("==================== 进入发财付账户余额查询接口 ====================");
        // 必填域校验
        FpayHelper.checkQueryBalanceEmpty(fpay);

        // 合法性校验
        FpayHelper.checkQueryBalanceValid(fpay);

        Resp resp = Resp.RESP_CO_0000;

        // 查询账户
        MerchAcct merchAcct = merchAcctService.findMerAcctByMerchCoAndAcctNo(merchCo, fpay.getAcctNo());
        if (merchAcct == null) {
            resp = Resp.RESP_CO_0014;
        } else {
            fpay.setAcctNm(merchAcct.getMerchAcctNm());
            fpay.setIdTp(merchAcct.getMerchIdTp());
            fpay.setIdNo(merchAcct.getMerchIdNo());
            fpay.setMobile(merchAcct.getMerchMobile());
            fpay.setBalance(merchAcct.getBalance());
        }

        // 回写响应数据
        writeCommonResp(fpay, resp);

        log.info("==================== 离开发财付账户余额查询接口 ====================");
    }

    /**
     * 交易落库
     *
     * @param merchCo 商户号
     * @param tranCo  交易码
     * @param tranSt  交易状态
     * @param fpay    请求
     * @throws Exception 未知异常
     */
    private void writeCommon(String merchCo, String tranCo, String tranSt, Fpay fpay) throws Exception {
        Command command = new Command();
        PropertyUtils.copyProperties(command, fpay);

        command.setMerchCo(merchCo);
        command.setTranCo(tranCo);
        command.setMerchSerialNo(fpay.getSerialNo());
        command.setFpayDate(DateUtil.getDate());
        command.setFpaySerialNo(fpayHelper.genSerialNo());
        command.setTranSt(tranSt);

        commandService.saveCommand(command);
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
