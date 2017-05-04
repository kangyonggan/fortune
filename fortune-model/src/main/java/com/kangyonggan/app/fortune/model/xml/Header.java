package com.kangyonggan.app.fortune.model.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * @author kangyonggan
 * @since 5/4/17
 */
@XStreamAlias("header")
@Data
public class Header {

    /**
     * 商户号
     */
    private String merchCo;

    /**
     * 交易码
     */
    private String tranCo;

    /**
     * 请求方流水号
     */
    private String serialNo;

    /**
     * 请求方流水号
     */
    private String reqDate;

    /**
     * 请求方交易时间
     */
    private String reqTime;

    /**
     * 签名
     */
    private String signature;

    /**
     * 响应码
     */
    private String respCo;

    /**
     * 响应信息
     */
    private String respMsg;


}
