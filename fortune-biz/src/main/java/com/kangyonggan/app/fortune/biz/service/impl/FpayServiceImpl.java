package com.kangyonggan.app.fortune.biz.service.impl;

import com.kangyonggan.app.fortune.biz.service.FpayService;
import com.kangyonggan.app.fortune.common.exception.ParseException;
import com.kangyonggan.app.fortune.model.annotation.LogTime;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * @author kangyonggan
 * @since 5/4/17
 */
@Service
@Log4j2
public class FpayServiceImpl implements FpayService {

    @Override
    @LogTime
    public String execute(String reqXml) throws ParseException, Exception {
        return null;
    }
}
