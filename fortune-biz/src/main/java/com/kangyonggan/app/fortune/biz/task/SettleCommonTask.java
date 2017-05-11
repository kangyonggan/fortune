package com.kangyonggan.app.fortune.biz.task;

import com.kangyonggan.app.fortune.biz.service.CommandService;
import com.kangyonggan.app.fortune.biz.service.MerchantService;
import com.kangyonggan.app.fortune.common.util.FtpUtil;
import com.kangyonggan.app.fortune.model.constants.FtpType;
import com.kangyonggan.app.fortune.model.vo.Merchant;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 凌晨1点生成对账文件并推送,如果生成失败，可调接口手动推送
 *
 * @author kangyonggan
 * @since 2017/5/10 0010
 */
@Component
@Log4j2
public class SettleCommonTask {

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private CommandService commandService;

    /**
     * 普通对账
     * cron表达式：* * * * * *（秒 分 时 日 月 星期）
     */
//    @Scheduled(cron = "0 0 1 * * *")
    @Scheduled(cron = "0 0/1 * * * *")
    public void executeCommon() {
        ftpType(FtpType.common);
    }

    /**
     * 基金公司对账
     * cron表达式：* * * * * *（秒 分 时 日 月 星期）
     */
//    @Scheduled(cron = "0 0 20/15 * * *")
    @Scheduled(cron = "0 0/1 * * * *")
    public void executeFund() {
        ftpType(FtpType.fund);
    }

    private void ftpType(FtpType ftpType) {
        // 查找凌晨一点需要推送对账文件的商户
        List<Merchant> merchants = merchantService.findMerchantsByFtpType(ftpType.name());
        log.info("一共{}个商户需要凌晨一点推送对账文件", merchants.size());

        // 逐个处理
        for (Merchant merchant : merchants) {
            log.info("正在生成{}的对账文件", merchant.getMerchNm());
            // 生成商户的对账文件
            String filePath = null;
            try {
                filePath = commandService.genSettleFile(merchant, ftpType.name());
                log.info("商户{}的对账文件生成完了，文件路径:{}", merchant.getMerchNm(), filePath);
            } catch (Exception e) {
                log.info("生成对账文件异常", e);
            }

            // 主动推送到商户ftp服务器
            if (filePath == null) {
                try {
                    FtpUtil.push(merchant.getFtpHost(), merchant.getFtpUser(), merchant.getFtpPwd(), merchant.getFtpDir(), filePath);
                    log.info("向{}推送对账文件完成", merchant.getMerchNm());
                } catch (Exception e) {
                    log.info("向" + merchant.getMerchNm() + "推送对账文件异常", e);
                }
            }
        }
    }

}
