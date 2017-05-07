package com.kangyonggan.app.fortune.biz.service.impl;

import com.kangyonggan.app.fortune.biz.service.CommandService;
import com.kangyonggan.app.fortune.common.util.DateUtil;
import com.kangyonggan.app.fortune.model.annotation.LogTime;
import com.kangyonggan.app.fortune.model.constants.AppConstants;
import com.kangyonggan.app.fortune.model.constants.TranSt;
import com.kangyonggan.app.fortune.model.vo.Command;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kangyonggan
 * @since 5/5/17
 */
@Service
@Log4j2
public class CommandServiceImpl extends BaseService<Command> implements CommandService {

    @Override
    @LogTime
    public void saveCommand(Command command) {
        myMapper.insertSelective(command);
    }

    @Override
    @LogTime
    public Command findCommandBySerialNo(String serialNo) {
        Command command = new Command();
        command.setMerchSerialNo(serialNo);
        command.setIsDeleted(AppConstants.IS_DELETED_NO);

        return myMapper.selectOne(command);
    }

    @Override
    public void updateCommandsToSuccess() {
        Command command = new Command();
        command.setTranSt(TranSt.Y.name());

        List<String> list = new ArrayList();
        list.add(TranSt.E.name());
        list.add(TranSt.I.name());

        Example example = new Example(Command.class);
        example.createCriteria().andIn("tranSt", list);

        myMapper.updateByExampleSelective(command, example);
    }

    @Override
    public void updateCommandsToFailure() {
        Command command = new Command();
        command.setTranSt(TranSt.F.name());

        Example example = new Example(Command.class);
        example.createCriteria().andEqualTo("tranSt", TranSt.N.name()).andLessThan("createdTime", DateUtil.plusMinutes(-10));

        myMapper.updateByExampleSelective(command, example);
    }
}
