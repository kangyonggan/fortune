package com.kangyonggan.app.fortune.model.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author kangyonggan
 * @since 5/4/17
 */
@XStreamAlias("body")
@Data
public class Body {

    /**
     * 卡号/对公账号
     */
    private String acctNo;

    /**
     * 户名
     */
    private String acctNm;

    /**
     * 证件类型
     */
    private String idTp;

    /**
     * 证件号码
     */
    private String idNo;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 协议号
     */
    private String protocolNo;

    /**
     * 币种
     */
    private String currCo;

    /**
     * 交易金额
     */
    private BigDecimal amount;

    /**
     * 付款方式账户类型
     */
    private String sndrAcctTp;

    /**
     * 清算日期
     */
    private String settleDate;

    /**
     * 备注
     */
    private String remark;

    /**
     * 预留字段1
     */
    private String resv1;

    /**
     * 预留字段2
     */
    private String resv2;

    /**
     * 发财付交易日期
     */
    private String fpayDate;

    /**
     * 发财付流水号
     */
    private String fpaySerialNo;

    /**
     * 发财付清算日期
     */
    private String FpaySettleDate;

    /**
     * 收款方式账户类型
     */
    private String rcvrAcctTp;

    /**
     * 原交易流水号
     */
    private String orgnSerialNo;

    /**
     * 原交易状态
     */
    private String tranSt;

    /**
     * 账户余额
     */
    private BigDecimal balance;

}
