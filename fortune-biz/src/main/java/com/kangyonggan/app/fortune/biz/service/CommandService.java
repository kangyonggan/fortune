package com.kangyonggan.app.fortune.biz.service;

import com.kangyonggan.app.fortune.model.vo.Command;

/**
 * @author kangyonggan
 * @since 5/5/17
 */
public interface CommandService {

    /**
     * 交易落库
     *
     * @param command
     */
    void saveCommand(Command command);

    /**
     * 查找交易
     *
     * @param serialNo
     * @return
     */
    Command findCommandBySerialNo(String serialNo);

    /**
     * 把I和E的交易更新为Y
     */
    void updateCommandsToSuccess();

    /**
     * 把10分钟之前的N更新为F
     */
    void updateCommandsToFailure();
}
