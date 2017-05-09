package com.kangyonggan.app.fortune.biz.service;

import com.kangyonggan.app.fortune.model.dto.CommandDto;
import com.kangyonggan.app.fortune.model.vo.Command;

import java.text.ParseException;
import java.util.List;

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

    /**
     * 搜索交易
     *
     * @param pageNum
     * @param startDate
     * @param endDate
     * @param tranSt
     * @return
     * @throws ParseException
     */
    List<Command> searchCommands(int pageNum, String startDate, String endDate, String tranSt) throws ParseException;

    /**
     * 查询流水详情
     *
     * @param id
     * @return
     */
    CommandDto findCommandById(Long id);
}
