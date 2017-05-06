package com.kangyonggan.app.fortune.biz.service.impl;

import com.kangyonggan.app.fortune.biz.service.CommandService;
import com.kangyonggan.app.fortune.biz.service.FpayHelper;
import com.kangyonggan.app.fortune.biz.service.FpayService;
import com.kangyonggan.app.fortune.biz.service.ProtocolService;
import com.kangyonggan.app.fortune.common.exception.BuildException;
import com.kangyonggan.app.fortune.common.util.DateUtil;
import com.kangyonggan.app.fortune.common.util.XStreamUtil;
import com.kangyonggan.app.fortune.model.constants.AppConstants;
import com.kangyonggan.app.fortune.model.constants.RespCo;
import com.kangyonggan.app.fortune.model.constants.TranCo;
import com.kangyonggan.app.fortune.model.constants.TranSt;
import com.kangyonggan.app.fortune.model.vo.Command;
import com.kangyonggan.app.fortune.model.vo.Protocol;
import com.kangyonggan.app.fortune.model.xml.Body;
import com.kangyonggan.app.fortune.model.xml.Fpay;
import com.kangyonggan.app.fortune.model.xml.Header;
import com.thoughtworks.xstream.XStream;
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

        RespCo resp = RespCo.RESP_CO_0000;
        // 更新交易状态, 交易金额后两位即响应码，没对应的响应码则为成功, 签约解约余额查询写死成功。
        commandService.updateComanndTranSt(header.getSerialNo(), resp.getTranSt());
        log.info("更新交易状态成功");

        // 组装响应报文
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

        RespCo resp = RespCo.RESP_CO_0000;
        // 更新交易状态, 交易金额后两位即响应码，没对应的响应码则为成功, 签约解约余额查询写死成功。
        commandService.updateComanndTranSt(header.getSerialNo(), resp.getTranSt());
        log.info("更新交易状态成功");

        // 组装响应报文
        header.setRespCo(resp.getRespCo());
        header.setRespMsg(resp.getRespMsg());
        body.setProtocolNo(protocolNo);
        log.info("==================== 离开发财付平台解约入口 ====================");
    }

    @Override
    public void pay(Fpay fpay) throws Exception {
        log.info("==================== 进入发财付平台单笔代扣入口 ====================");
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

        commandService.updateComanndTranSt(header.getSerialNo(), resp.getTranSt());
        log.info("更新交易状态成功");

        // 组装响应报文
        header.setRespCo(resp.getRespCo());
        header.setRespMsg(resp.getRespMsg());
        body.setFpaySettleDate(body.getSettleDate());

        log.info("==================== 离开发财付平台单笔代扣入口 ====================");
    }

    @Override
    public void redeem(Fpay fpay) throws Exception {
    }

    @Override
    public void query(Fpay fpay) throws Exception {
    }

    @Override
    public void queryBalance(Fpay fpay) throws Exception {
    }
}
