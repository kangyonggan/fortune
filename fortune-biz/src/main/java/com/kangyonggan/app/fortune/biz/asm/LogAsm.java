package com.kangyonggan.app.fortune.biz.asm;

import lombok.extern.log4j.Log4j2;

/**
 * @author kangyonggan
 * @since 5/18/17
 */
@Log4j2
public class LogAsm {

    public static void before() {
        log.info("========== before ==========");
    }

}
