package com.kangyonggan.app.fortune.model.constants;

import lombok.Getter;

/**
 * @author kangyonggan
 * @since 5/5/17
 */
public enum Resp {

    RESP_CO_0000("0000", "交易成功", "Y"),
    RESP_CO_0001("0001", "交易处理中", "I"),
    RESP_CO_0002("0002", "解密异常", "F"),
    RESP_CO_0003("0003", "解析异常", "F"),
    RESP_CO_0004("0004", "验签异常", "F"),
    RESP_CO_0005("0005", "验签失败", "F"),
    RESP_CO_0006("0006", "未知交易", "F"),
    RESP_CO_0007("0007", "必填域缺失", "F"),
    RESP_CO_0008("0008", "数据不合法", "F"),
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

    Resp(String respCo, String respMsg, String tranSt) {
        this.respCo = respCo;
        this.respMsg = respMsg;
        this.tranSt = tranSt;
    }

    public static Resp getRespCo(String respCo) {
        return Resp.valueOf("RESP_CO_" + respCo);
    }
}
