package com.kangyonggan.app.fortune.biz.service;

import com.kangyonggan.app.fortune.model.vo.Command;
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
     * 更新交易状态
     *
     * @param serialNo
     * @param tranSt
     */
    void updateComanndTranSt(String serialNo, String tranSt);

    /**
     * 查找交易
     *
     * @param serialNo
     * @return
     */
    Command findCommandBySerialNo(String serialNo);
}
