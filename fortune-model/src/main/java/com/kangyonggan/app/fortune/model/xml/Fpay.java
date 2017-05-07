package com.kangyonggan.app.fortune.model.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author kangyonggan
 * @since 5/4/17
 */
@XStreamAlias("fpay")
@Data
public class Fpay {

    /**
     * 请求方流水号
     */
    private String serialNo;

    /**
     * 原交易流水号
     */
    private String orgnSerialNo;

    /**
     * 请求方交易日期
     */
    private String reqDate;

    /**
     * 请求方交易时间
     */
    private String reqTime;

    /**
     * 卡号
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
     * 卡
     */
    private String mobile;

    /**
     * 币种
     */
    private String currCo;

    /**
     * 协议号
     */
    private String protocolNo;

    /**
     * 清算日期
     */
    private String settleDate;

    /**
     * 账户类型
     */
    private String acctTp;

    /**
     * 交易金额
     */
    private BigDecimal amount;

    /**
     * 响应码
     */
    private String respCo;

    /**
     * 响应描述
     */
    private String respMsg;

    /**
     * 发财付交易日期
     */
    private String fpayDate;

    /**
     * 发财付交易流水
     */
    private String fpaySerialNo;

    /**
     * 交易状态
     */
    private String tranSt;

    /**
     * 余额
     */
    private BigDecimal balance;

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

}
