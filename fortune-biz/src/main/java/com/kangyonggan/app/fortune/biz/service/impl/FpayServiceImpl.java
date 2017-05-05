package com.kangyonggan.app.fortune.biz.service.impl;

import com.kangyonggan.app.fortune.biz.service.CommandService;
import com.kangyonggan.app.fortune.biz.service.FpayService;
import com.kangyonggan.app.fortune.biz.service.ProtocolService;
import com.kangyonggan.app.fortune.common.exception.BuildException;
import com.kangyonggan.app.fortune.common.util.DateUtil;
import com.kangyonggan.app.fortune.common.util.XStreamUtil;
import com.kangyonggan.app.fortune.model.vo.Protocol;
import com.kangyonggan.app.fortune.model.xml.Body;
import com.kangyonggan.app.fortune.model.xml.Fpay;
import com.kangyonggan.app.fortune.model.xml.Header;
import com.thoughtworks.xstream.XStream;
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
    private ProtocolService protocolService;

    @Autowired
    private CommandService commandService;

    @Override
    public String sign(Fpay fpay) throws BuildException, Exception {
        Header header = fpay.getHeader();
        String merchCo = header.getMerchCo();

        // 判断是否已经有签约记录
        Body body = fpay.getBody();
        Protocol protocol = protocolService.findProtocolByMerchCoAndAcctNo(merchCo, body.getAcctNo());
        if (protocol == null) {
            // TODO 第一次签约
        } else {
            // TODO 重复签约或解约后再次签约
        }

        // 更新交易状态

        // 组装响应报文


        XStream xStream = XStreamUtil.getXStream();
        xStream.processAnnotations(Fpay.class);
        return xStream.toXML(fpay);
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
