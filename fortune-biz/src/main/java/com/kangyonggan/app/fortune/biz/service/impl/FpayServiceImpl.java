package com.kangyonggan.app.fortune.biz.service.impl;

import com.kangyonggan.app.fortune.biz.service.*;
import com.kangyonggan.app.fortune.common.util.DateUtil;
import com.kangyonggan.app.fortune.model.constants.RespCo;
import com.kangyonggan.app.fortune.model.vo.Command;
import com.kangyonggan.app.fortune.model.vo.MerchAcct;
import com.kangyonggan.app.fortune.model.vo.Merchant;
import com.kangyonggan.app.fortune.model.vo.Protocol;
import com.kangyonggan.app.fortune.model.xml.Body;
import com.kangyonggan.app.fortune.model.xml.Fpay;
import com.kangyonggan.app.fortune.model.xml.Header;
import lombok.extern.log4j.Log4j2;
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
    private CommandService commandService;

    @Autowired
    private FpayHelper fpayHelper;

    @Autowired
    private MerchAcctService merchAcctService;

    @Override
    public void sign(Fpay fpay) throws Exception {
        log.info("==================== 进入发财付平台签约入口 ====================");
        Header header = fpay.getHeader();
        String merchCo = header.getMerchCo();

        // 判断是否已经有签约记录
        Body body = fpay.getBody();
        Protocol protocol = protocolService.findProtocolByMerchCoAndAcctNo(merchCo, body.getAcctNo());
        String protocolNo;
        if (protocol == null) {
            // 第一次签约
            protocolNo = fpayHelper.genProtocolNo();
            Protocol prot = new Protocol();
            prot.setMerchCo(header.getMerchCo());
            prot.setProtocolNo(protocolNo);
            prot.setAcctNo(body.getAcctNo());
            prot.setAcctNm(body.getAcctNm());
            prot.setIdNo(body.getIdNo());
            prot.setIdTp(body.getIdTp());
            prot.setMobile(body.getMobile());
            // 协议有效期
            prot.setExpiredTime(DateUtil.plusYears(10));// 10年

            protocolService.saveProtocol(prot);
            log.info("新协议保存成功");
        } else {
            // 重复签约
            protocolNo = protocol.getProtocolNo();
            Protocol prot = new Protocol();
            prot.setProtocolNo(protocolNo);
            prot.setIsUnsign((byte) 0);
            // 协议有效期
            prot.setExpiredTime(DateUtil.plusYears(10));// 10年
            protocolService.updateProtocolByProtocolNo(prot);
            log.info("重复签约，已激活协议号");
        }

        // 组装响应报文
        RespCo resp = RespCo.RESP_CO_0000;
        header.setRespCo(resp.getRespCo());
        header.setRespMsg(resp.getRespMsg());
        body.setProtocolNo(protocolNo);
        log.info("==================== 离开发财付平台签约入口 ====================");
    }

    @Override
    public void unsign(Fpay fpay) throws Exception {
        log.info("==================== 进入发财付平台解约入口 ====================");
        Header header = fpay.getHeader();
        String merchCo = header.getMerchCo();

        // 取出协议号回送
        Body body = fpay.getBody();
        Protocol protocol = protocolService.findProtocolByMerchCoAndAcctNo(merchCo, body.getAcctNo());
        String protocolNo = protocol.getProtocolNo();
        log.info("解约的协议号为：{}", protocolNo);

        // 更新协议状态
        Protocol prot = new Protocol();
        prot.setProtocolNo(protocolNo);
        prot.setIsUnsign((byte) 1);
        protocolService.updateProtocolByProtocolNo(prot);
        log.info("解约协议成功");

        // 组装响应报文
        RespCo resp = RespCo.RESP_CO_0000;
        header.setRespCo(resp.getRespCo());
        header.setRespMsg(resp.getRespMsg());
        body.setProtocolNo(protocolNo);
        log.info("==================== 离开发财付平台解约入口 ====================");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void pay(Fpay fpay) throws Exception {
        log.info("==================== 进入发财付平台单笔代扣入口 ====================");
        Header header = fpay.getHeader();
        Body body = fpay.getBody();

        // 更新交易状态, 交易金额后两位即响应码，没对应的响应码则为成功, 签约解约余额查询写死成功。
        String amount = body.getAmount().toString();
        log.info("交易金额为：{}", amount);

        RespCo resp;
        MerchAcct merchAcct = merchAcctService.findMerAcctByMerchNo(header.getMerchCo());
        if (body.getAmount().compareTo(merchAcct.getBalance()) > 0) {
            resp = RespCo.RESP_CO_0024;
            log.info(resp.getRespMsg());
        } else {
            int index = amount.lastIndexOf(".");
            String end = "0000";
            if (index != -1) {
                end = "00" + amount.substring(index + 1);
                end = StringUtils.rightPad(end, 4, "0");
            }
            resp = RespCo.getRespCo(end);


            if ("Y,E,I".indexOf(resp.getTranSt()) > -1) {
                // 注定成功的交易才扣头寸
                MerchAcct ma = new MerchAcct();
                ma.setMerchCo(header.getMerchCo());
                ma.setBalance(merchAcct.getBalance().subtract(body.getAmount()));

                merchAcctService.updateMerchAcct(ma);
                log.info("商户头寸已扣除");
            }
        }

        commandService.updateComanndTranSt(header.getSerialNo(), resp.getTranSt());
        log.info("更新交易状态成功");

        // 组装响应报文
        header.setRespCo(resp.getRespCo());
        header.setRespMsg(resp.getRespMsg());
        body.setFpaySettleDate(body.getSettleDate());

        log.info("==================== 离开发财付平台单笔代扣入口 ====================");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void redeem(Fpay fpay) throws Exception {
        log.info("==================== 进入发财付平台单笔代付入口 ====================");
        Header header = fpay.getHeader();
        Body body = fpay.getBody();

        // 更新交易状态, 交易金额后两位即响应码，没对应的响应码则为成功, 签约解约余额查询写死成功。
        String amount = body.getAmount().toString();
        log.info("交易金额为：{}", amount);

        int index = amount.lastIndexOf(".");
        String end = "0000";
        if (index != -1) {
            end = "00" + amount.substring(index + 1);
            end = StringUtils.rightPad(end, 4, "0");
        }
        RespCo resp = RespCo.getRespCo(end);

        if ("Y,E,I".indexOf(resp.getTranSt()) > -1) {
            // 注定成功的交易才加头寸
            MerchAcct merchAcct = merchAcctService.findMerAcctByMerchNo(header.getMerchCo());

            // 加头寸
            MerchAcct ma = new MerchAcct();
            ma.setMerchCo(header.getMerchCo());
            ma.setBalance(merchAcct.getBalance().add(body.getAmount()));

            merchAcctService.updateMerchAcct(ma);
            log.info("商户头寸已增加");
        }

        commandService.updateComanndTranSt(header.getSerialNo(), resp.getTranSt());
        log.info("更新交易状态成功");

        // 组装响应报文
        header.setRespCo(resp.getRespCo());
        header.setRespMsg(resp.getRespMsg());
        body.setFpaySettleDate(body.getSettleDate());

        log.info("==================== 离开发财付平台单笔代付入口 ====================");
    }

    @Override
    public void query(Fpay fpay) throws Exception {
        log.info("==================== 进入发财付平台交易查询入口 ====================");
        Header header = fpay.getHeader();
        Body body = fpay.getBody();

        Command command = commandService.findCommandBySerialNo(body.getOrgnSerialNo());

        RespCo resp = RespCo.RESP_CO_0000;
        if (command == null) {
            resp = RespCo.RESP_CO_0023;
        }

        // 组装响应报文
        header.setRespCo(resp.getRespCo());
        header.setRespMsg(resp.getRespMsg());

        if (command != null) {
            body.setTranSt(command.getTranSt());
            body.setProtocolNo(command.getProtocolNo());
            body.setCurrCo(command.getCurrco());
            body.setAmount(command.getAmount());
            body.setFpayDate(command.getFpayDate());
            body.setFpaySettleDate(command.getSettleDate());
        }

        log.info("==================== 离开发财付平台交易查询入口 ====================");
    }

    @Override
    public void queryBalance(Fpay fpay) throws Exception {
        log.info("==================== 进入发财付平台账户余额查询入口 ====================");
        Header header = fpay.getHeader();
        Body body = fpay.getBody();

        MerchAcct merchAcct = merchAcctService.findMerAcctByMerchNoAndAcctNo(header.getMerchCo(), body.getAcctNo());
        RespCo resp = RespCo.RESP_CO_0000;

        // 组装响应报文
        header.setRespCo(resp.getRespCo());
        header.setRespMsg(resp.getRespMsg());

        body.setBalance(merchAcct.getBalance());
        body.setAcctNo(merchAcct.getMerchAcctNo());
        body.setAcctNm(merchAcct.getMerchAcctNm());
        body.setIdTp(merchAcct.getMerchIdTp());
        body.setIdNo(merchAcct.getMerchIdNo());
        body.setMobile(merchAcct.getMerchMobile());

        log.info("==================== 离开发财付平台账户余额查询入口 ====================");
    }
}
