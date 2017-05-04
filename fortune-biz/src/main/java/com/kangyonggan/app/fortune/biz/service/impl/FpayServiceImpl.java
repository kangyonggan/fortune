package com.kangyonggan.app.fortune.biz.service.impl;

import com.kangyonggan.app.fortune.biz.service.*;
import com.kangyonggan.app.fortune.common.exception.BuildException;
import com.kangyonggan.app.fortune.model.vo.Merchant;
import com.kangyonggan.app.fortune.model.vo.Protocol;
import com.kangyonggan.app.fortune.model.vo.Trans;
import com.kangyonggan.app.fortune.model.xml.Body;
import com.kangyonggan.app.fortune.model.xml.Fpay;
import com.kangyonggan.app.fortune.model.xml.Header;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author kangyonggan
 * @since 5/4/17
 */
@Service
@Log4j2
public class FpayServiceImpl implements FpayService {

    @Autowired
    private FpayHelper fpayHelper;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private TransService transService;

    @Autowired
    private ProtocolService protocolService;

    @Override
    public String sign(Fpay fpay) throws BuildException, Exception {
        Header header = fpay.getHeader();
        String merchCo = header.getMerchCo();
        log.info("请求的商户号为：{}", merchCo);

        // 查询商户信息
        Merchant merchant = merchantService.findMerchantByMerchCo(merchCo);
        if (merchant == null) {
            log.info("商户信息不存在：{}", merchCo);
            return fpayHelper.buildErrorXml(fpay, "0010");
        }

        // 查询商户的交易类型
        String tranCo = header.getTranCo();
        Trans trans = transService.findTransByMerchCoAndTranCo(merchCo, tranCo);
        if (trans == null) {
            log.info("商户不支持此交易:{}--->{}", merchCo, tranCo);
            return fpayHelper.buildErrorXml(fpay, "0011");
        }

        // 判断交易是否已暂停
        if (trans.getIsPaused() == 1) {
            log.info("商户此交易已暂停:{}--->{}", merchCo, tranCo);
            return fpayHelper.buildErrorXml(fpay, "0012");
        }

        // 判断是否已经有签约记录
        Body body = fpay.getBody();
        Protocol protocol = protocolService.findProtocolByMerchCoAndAcctNo(merchCo, body.getAcctNo());
        if (protocol == null) {
            // TODO 第一次签约
        } else {
            // TODO 重复签约或解约后再次签约
        }

        // 组装响应报文

        // 更新交易状态

        return null;
    }

    @Override
    public String unsign(Fpay fpay) throws BuildException, Exception {
        return null;
    }

    @Override
    public String pay(Fpay fpay) throws BuildException, Exception {
        return null;
    }

    @Override
    public String redeem(Fpay fpay) throws BuildException, Exception {
        return null;
    }

    @Override
    public String query(Fpay fpay) throws BuildException, Exception {
        return null;
    }

    @Override
    public String queryBalance(Fpay fpay) throws BuildException, Exception {
        return null;
    }
}
