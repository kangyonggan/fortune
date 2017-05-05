package com.kangyonggan.app.fortune.biz.service.impl;

import com.kangyonggan.app.fortune.biz.service.CommandService;
import com.kangyonggan.app.fortune.biz.service.RedisService;
import com.kangyonggan.app.fortune.biz.util.PropertiesUtil;
import com.kangyonggan.app.fortune.common.util.DateUtil;
import com.kangyonggan.app.fortune.model.constants.AppConstants;
import com.kangyonggan.app.fortune.model.vo.Command;
import com.kangyonggan.app.fortune.model.xml.Body;
import com.kangyonggan.app.fortune.model.xml.Fpay;
import com.kangyonggan.app.fortune.model.xml.Header;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author kangyonggan
 * @since 5/5/17
 */
@Service
@Log4j2
public class CommandServiceImpl extends BaseService<Command> implements CommandService {

    /**
     * redis键的前缀
     */
    private String prefix = PropertiesUtil.getProperties("redis.prefix") + ":";

    @Autowired
    private RedisService redisService;

    @Override
    public void saveCommand(Fpay fpay) throws Exception {
        Header header = fpay.getHeader();
        Body body = fpay.getBody();

        Command command = new Command();
        PropertyUtils.copyProperties(command, header);
        PropertyUtils.copyProperties(command, body);

        // 拷贝不同的属性
        command.setMerchSerialNo(header.getSerialNo());

        // 发财付相关属性
        command.setFpaySerialNo(genSerialNo());
        command.setFpayDate(DateUtil.getDate());
        if (StringUtils.isEmpty(command.getSettleDate())) {
            command.setSettleDate(DateUtil.getDate());
        }

        super.insertSelective(command);
        log.info("交易落库成功");
    }

    /**
     * 生成流水号
     *
     * @return
     */
    private String genSerialNo() {
        String nextVal = String.valueOf(redisService.incr(prefix + AppConstants.COMMAND_SERIAL_NO));
        String currentDate = DateUtil.getDate();

        return currentDate + StringUtils.leftPad(nextVal, 12, "0");
    }
}
