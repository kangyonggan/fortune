package com.kangyonggan.app.fortune.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.kangyonggan.app.fortune.biz.service.CommandService;
import com.kangyonggan.app.fortune.common.util.DateUtil;
import com.kangyonggan.app.fortune.mapper.CommandMapper;
import com.kangyonggan.app.fortune.model.annotation.LogTime;
import com.kangyonggan.app.fortune.model.constants.AppConstants;
import com.kangyonggan.app.fortune.model.constants.TranSt;
import com.kangyonggan.app.fortune.model.dto.CommandDto;
import com.kangyonggan.app.fortune.model.vo.Command;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kangyonggan
 * @since 5/5/17
 */
@Service
@Log4j2
public class CommandServiceImpl extends BaseService<Command> implements CommandService {

    @Autowired
    private CommandMapper commandMapper;

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

    @Override
    @LogTime
    public List<Command> searchCommands(int pageNum, String startDate, String endDate, String tranSt) throws ParseException {
        Example example = new Example(Command.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(tranSt)) {
            criteria.andEqualTo("tranSt", tranSt);
        }
        if (StringUtils.isNotEmpty(startDate)) {
            criteria.andGreaterThanOrEqualTo("createdTime", DateUtil.fromDate(startDate));
        }
        if (StringUtils.isNotEmpty(endDate)) {
            criteria.andLessThanOrEqualTo("createdTime", DateUtil.fromDate(endDate));
        }
        criteria.andEqualTo("isDeleted", AppConstants.IS_DELETED_NO);
        example.setOrderByClause("id desc");

        PageHelper.startPage(pageNum, AppConstants.PAGE_SIZE);
        return myMapper.selectByExample(example);
    }

    @Override
    @LogTime
    public CommandDto findCommandById(Long id) {
        return commandMapper.selectCommandById(id);
    }
}
