package com.kangyonggan.app.fortune.biz.service;

import com.kangyonggan.app.fortune.model.xml.Fpay;

/**
 * @author kangyonggan
 * @since 5/5/17
 */
public interface CommandService {

    /**
     * 保存交易指令
     *
     * @param fpay
     * @throws Exception
     */
    void saveCommand(Fpay fpay) throws Exception;

    /**
     * 更新
     *
     * @param serialNo
     * @param fullDateTime
     */
    void updateCommandProtocolNo(String serialNo, String fullDateTime);
}
