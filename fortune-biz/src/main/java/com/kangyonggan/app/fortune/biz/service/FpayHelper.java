package com.kangyonggan.app.fortune.biz.service;

import com.kangyonggan.app.fortune.biz.util.PropertiesUtil;
import com.kangyonggan.app.fortune.common.util.DateUtil;
import com.kangyonggan.app.fortune.common.util.XStreamUtil;
import com.kangyonggan.app.fortune.common.util.XmlUtil;
import com.kangyonggan.app.fortune.model.constants.AppConstants;
import com.kangyonggan.app.fortune.model.constants.DictionaryType;
import com.kangyonggan.app.fortune.model.constants.RespCo;
import com.kangyonggan.app.fortune.model.constants.TranCo;
import com.kangyonggan.app.fortune.model.vo.Merchant;
import com.kangyonggan.app.fortune.model.vo.Protocol;
import com.kangyonggan.app.fortune.model.vo.Trans;
import com.kangyonggan.app.fortune.model.xml.Body;
import com.kangyonggan.app.fortune.model.xml.Fpay;
import com.kangyonggan.app.fortune.model.xml.Header;
import com.thoughtworks.xstream.XStream;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kangyonggan
 * @since 2017/5/4 0004
 */
@Service
@Log4j2
public class FpayHelper {

    /**
     * redis键的前缀
     */
    private String prefix = PropertiesUtil.getProperties("redis.prefix") + ":";

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private TransService transService;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ProtocolService protocolService;

    /**
     * 构建异常报文
     *
     * @param reqFpay
     * @param respCode
     * @return
     * @throws Exception
     */
    public String buildErrorXml(Fpay reqFpay, String respCode) throws Exception {
        // 响应码
        RespCo resp = RespCo.getRespCo(respCode);

        XStream xStream = XStreamUtil.getXStream();
        xStream.processAnnotations(Fpay.class);

        // 响应头
        Header header = new Header();
        if (reqFpay != null) {
            header.setMerchCo(reqFpay.getHeader().getMerchCo());
            header.setTranCo(reqFpay.getHeader().getTranCo());
            header.setSerialNo(reqFpay.getHeader().getSerialNo());
        } else {
            header.setMerchCo("");
            header.setTranCo("");
            header.setSerialNo("");
        }
        header.setRespCo(resp.getRespCo());
        header.setRespMsg(resp.getRespMsg());

        // 响应报文整体
        Fpay respFpay = new Fpay();
        respFpay.setHeader(header);

        // 转xml
        String respXml = xStream.toXML(respFpay);

        // 格式化xml
        respXml = XmlUtil.format(respXml);
        return respXml;
    }

    /**
     * 入参非空校验， 顺带写入缺省值
     *
     * @param fpay
     * @return
     */
    public boolean validEmpty(Fpay fpay) {
        if (fpay == null) {
            return false;
        }

        Header header = fpay.getHeader();
        Body body = fpay.getBody();

        if (fpay.getHeader() == null) {
            return false;
        }

        if (fpay.getBody() == null) {
            return false;
        }

        // 交易header
        if (StringUtils.isEmpty(header.getMerchCo())) {
            return false;
        }

        if (StringUtils.isEmpty(header.getTranCo())) {
            return false;
        }

        if (StringUtils.isEmpty(header.getSerialNo())) {
            return false;
        }

        if (StringUtils.isEmpty(header.getReqDate())) {
            return false;
        }

        if (StringUtils.isEmpty(header.getReqTime())) {
            return false;
        }

        // 检验body
        if (TranCo.K001.name().equals(header.getTranCo()) || TranCo.K002.name().equals(header.getTranCo())) {
            // 签约解约相关校验
            if (StringUtils.isEmpty(body.getAcctNo())) {
                return false;
            }
            if (StringUtils.isEmpty(body.getAcctNm())) {
                return false;
            }
            if (StringUtils.isEmpty(body.getIdTp())) {
                body.setIdTp("0");
            }
            if (StringUtils.isEmpty(body.getIdNo())) {
                return false;
            }
            if (StringUtils.isEmpty(body.getMobile())) {
                return false;
            }
        } else if (TranCo.K003.name().equals(header.getTranCo())) {
            if (StringUtils.isEmpty(body.getProtocolNo())) {
                return false;
            }
            if (StringUtils.isEmpty(body.getCurrCo())) {
                body.setCurrCo("00");
            }
            if (body.getAmount() == null) {
                return false;
            }
            if (StringUtils.isEmpty(body.getSndrAcctTp())) {
                body.setSndrAcctTp("00");
            }
            if (StringUtils.isEmpty(body.getSettleDate())) {
                body.setSettleDate(DateUtil.getDate());
            }
        } else if (TranCo.K004.name().equals(header.getTranCo())) {
            if (StringUtils.isEmpty(body.getProtocolNo())) {
                return false;
            }
            if (StringUtils.isEmpty(body.getCurrCo())) {
                body.setCurrCo("00");
            }
            if (body.getAmount() == null) {
                return false;
            }
            if (StringUtils.isEmpty(body.getRcvrAcctTp())) {
                body.setRcvrAcctTp("00");
            }
            if (StringUtils.isEmpty(body.getSettleDate())) {
                body.setSettleDate(DateUtil.getDate());
            }
        } else if (TranCo.K005.name().equals(header.getTranCo())) {
            if (StringUtils.isEmpty(body.getOrgnSerialNo())) {
                return false;
            }
        } else if (TranCo.K006.name().equals(header.getTranCo())) {
            if (StringUtils.isEmpty(body.getAcctNo())) {
                return false;
            }
        }

        log.info("数据非空校验通过");
        return true;
    }

    /**
     * 入参合法性校验
     * isValid：校验是否通过
     * respCo：校验不通过时的错误码
     *
     * @param fpay
     * @return
     */
    public Map<String, String> validData(Fpay fpay) throws Exception {
        Map<String, String> result = new HashMap();
        result.put("isValid", "F");

        Merchant merchant = merchantService.findMerchantByMerchCo(fpay.getHeader().getMerchCo());
        if (merchant == null) {
            result.put("respCo", RespCo.RESP_CO_0013.getRespCo());
            return result;
        }

        Trans trans = transService.findTransByMerchCoAndTranCo(merchant.getMerchCo(), fpay.getHeader().getTranCo());
        if (trans == null) {
            result.put("respCo", RespCo.RESP_CO_0014.getRespCo());
            return result;
        }

        if (trans.getIsPaused() == 1) {
            result.put("respCo", RespCo.RESP_CO_0015.getRespCo());
            return result;
        }

        if (TranCo.K001.name().equals(fpay.getHeader().getTranCo())) {
            // 签约验证件类型
            if (!dictionaryService.exists(DictionaryType.ID_TP.name(), fpay.getBody().getIdTp())) {
                result.put("respCo", RespCo.RESP_CO_0016.getRespCo());
                return result;
            }
        }

        if (TranCo.K002.name().equals(fpay.getHeader().getTranCo())) {
            // 解约验证件类型
            if (!dictionaryService.exists(DictionaryType.ID_TP.name(), fpay.getBody().getIdTp())) {
                result.put("respCo", RespCo.RESP_CO_0016.getRespCo());
                return result;
            }

            // 解约验证协议号是否存在
            Protocol protocol = protocolService.findProtocolByMerchCoAndAcctNo(merchant.getMerchCo(), fpay.getBody().getAcctNo());
            if (protocol == null) {
                result.put("respCo", RespCo.RESP_CO_0022.getRespCo());
                return result;
            }
        }

        if (TranCo.K003.name().equals(fpay.getHeader().getTranCo()) || TranCo.K004.name().equals(fpay.getHeader().getTranCo())) {
            // 代扣代付验币种
            if (!dictionaryService.exists(DictionaryType.CURR_CO.name(), fpay.getBody().getCurrCo())) {
                result.put("respCo", RespCo.RESP_CO_0017.getRespCo());
                return result;
            }
        }

        result.put("isValid", "Y");
        log.info("数据合法性校验通过");
        return result;
    }

    /**
     * 生成流水号
     *
     * @return
     */
    public String genSerialNo() {
        String nextVal = String.valueOf(redisService.incr(prefix + AppConstants.COMMAND_SERIAL_NO));
        String currentDate = DateUtil.getDate();

        return currentDate + StringUtils.leftPad(nextVal, 12, "0");
    }

    /**
     * 生成协议号
     *
     * @return
     */
    public String genProtocolNo() {
        String nextVal = String.valueOf(redisService.incr(prefix + AppConstants.SIGN_PROTOCOL_NO));
        String currentDate = DateUtil.getDate();

        return currentDate + StringUtils.leftPad(nextVal, 40, "0");
    }
}
