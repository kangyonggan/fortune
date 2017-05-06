package com.kangyonggan.app.fortune.biz.service.impl;

import com.kangyonggan.app.fortune.biz.service.CommandService;
import com.kangyonggan.app.fortune.biz.service.FpayHelper;
import com.kangyonggan.app.fortune.common.util.DateUtil;
import com.kangyonggan.app.fortune.model.annotation.LogTime;
import com.kangyonggan.app.fortune.model.constants.AppConstants;
import com.kangyonggan.app.fortune.model.constants.TranCo;
import com.kangyonggan.app.fortune.model.constants.TranSt;
import com.kangyonggan.app.fortune.model.vo.Command;
import com.kangyonggan.app.fortune.model.xml.Body;
import com.kangyonggan.app.fortune.model.xml.Fpay;
import com.kangyonggan.app.fortune.model.xml.Header;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private FpayHelper fpayHelper;

    @Override
    public void saveCommand(Fpay fpay) throws Exception {
        Header header = fpay.getHeader();
        Body body = fpay.getBody();

        // 只有代扣和代付才落库
        if (!header.getTranCo().equals(TranCo.K003.name()) && !header.getTranCo().equals(TranCo.K004.name())) {
            return;
        }

        Command command = new Command();
        PropertyUtils.copyProperties(command, header);
        PropertyUtils.copyProperties(command, body);

        // 拷贝不同的属性
        command.setMerchSerialNo(header.getSerialNo());

        // 发财付相关属性
        command.setFpaySerialNo(fpayHelper.genSerialNo());
        command.setFpayDate(DateUtil.getDate());
        if (StringUtils.isEmpty(command.getSettleDate())) {
            command.setSettleDate(DateUtil.getDate());
        }

        body.setFpayDate(command.getFpayDate());
        body.setFpaySerialNo(command.getFpaySerialNo());

        myMapper.insertSelective(command);
        log.info("交易落库成功");
    }

    @Override
    @LogTime
    public void updateComanndTranSt(String serialNo, String tranSt) {
        Command command = new Command();
        command.setTranSt(tranSt);

        Example example = new Example(Command.class);
        example.createCriteria().andEqualTo("merchSerialNo", serialNo);

        myMapper.updateByExampleSelective(command, example);
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
