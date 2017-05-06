package com.kangyonggan.app.fortune.biz.task;

import com.kangyonggan.app.fortune.biz.service.CommandService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 1. 每隔2分钟把I、E的更新为Y
 * 2. 把10分钟之前的N更新为F
 *
 * @author kangyonggan
 * @since 2017/5/6 0006
 */
@Component
@Log4j2
public class CommandTask {

    @Autowired
    private CommandService commandService;

    /**
     * 每隔2分钟执行一次
     * cron表达式：* * * * * *（秒 分 时 日 月 星期）
     */
    @Scheduled(cron = "0 0/2 * * * *")
    public void execute() {
        log.info("定时任务启动，把I和E的交易更新为Y");
        commandService.updateCommandsToSuccess();

        log.info("定时任务启动，把10分钟之前的N更新为F");
        commandService.updateCommandsToFailure();
    }

}
