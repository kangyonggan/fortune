package com.kangyonggan.app.fortune.biz.service;

import com.kangyonggan.app.fortune.model.vo.Resp;

/**
 * @author kangyonggan
 * @since 5/4/17
 */
public interface RespService {

    /**
     * 查找错误码
     *
     * @param respCo
     * @return
     */
    Resp findRespByRespCo(String respCo);

}
