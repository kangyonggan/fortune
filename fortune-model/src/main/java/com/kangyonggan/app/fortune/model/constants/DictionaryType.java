package com.kangyonggan.app.fortune.model.constants;

import lombok.Getter;

/**
 * @author kangyonggan
 * @since 5/5/17
 */
public enum DictionaryType {

    TRANS_CO("TRANS_CO", "交易码"),
    ID_TP("ID_TP", "证件类型"),
    ACCT_TP("ACCT_TP", "账户类型"),
    CURR_CO("CURR_CO", "币种");

    /**
     * 类型
     */
    @Getter
    private final String type;

    /**
     * 类型名称
     */
    @Getter
    private final String name;

    DictionaryType(String type, String name) {
        this.type = type;
        this.name = name;
    }

}
