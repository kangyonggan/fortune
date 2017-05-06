package com.kangyonggan.app.fortune.model.constants;

import lombok.Getter;

/**
 * @author kangyonggan
 * @since 5/5/17
 */
public enum RespCo {

    RESP_CO_0000("0000", "交易成功", "Y"),
    RESP_CO_0001("0001", "交易处理中", "I"),
    RESP_CO_0002("0002", "接收报文异常", "F"),
    RESP_CO_0003("0003", "发送报文异常", "E"),
    RESP_CO_0004("0004", "解析报文异常", "F"),
    RESP_CO_0005("0005", "构建报文异常", "E"),
    RESP_CO_0006("0006", "必填字段缺失", "F"),
    RESP_CO_0007("0007", "交易重复", "F"),
    RESP_CO_0008("0008", "解密失败", "F"),
    RESP_CO_0009("0009", "验签失败", "F"),
    RESP_CO_0010("0010", "签名失败", "E"),
    RESP_CO_0011("0011", "加密失败", "E"),
    RESP_CO_0012("0012", "不支持的交易码", "F"),
    RESP_CO_0013("0013", "不存在的商户号", "F"),
    RESP_CO_0014("0014", "商户未开通此类交易", "F"),
    RESP_CO_0015("0015", "商户已暂停此类交易", "F"),
    RESP_CO_0016("0016", "不存在的证件类型", "F"),
    RESP_CO_0017("0017", "不存在的币种", "F"),
    RESP_CO_0018("0018", "单笔超限", "F"),
    RESP_CO_0019("0019", "日累计超限", "F"),
    RESP_CO_0020("0020", "余额不足", "F"),
    RESP_CO_0021("0021", "协议已解除或逾期", "F"),
    RESP_CO_0022("0022", "协议号不存在", "F"),
    RESP_CO_0023("0023", "原交易不存在", "F"),
    RESP_CO_9999("9999", "未知异常", "E");

    /**
     * 响应码
     */
    @Getter
    private final String respCo;

    /**
     * 响应消息
     */
    @Getter
    private final String respMsg;

    /**
     * 交易状态
     */
    @Getter
    private final String tranSt;

    RespCo(String respCo, String respMsg, String tranSt) {
        this.respCo = respCo;
        this.respMsg = respMsg;
        this.tranSt = tranSt;
    }

    public static RespCo getRespCo(String respCo) {
        return RespCo.valueOf("RESP_CO_" + respCo);
    }
}
