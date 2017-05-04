package com.kangyonggan.app.fortune.biz.service.impl;

import com.kangyonggan.app.fortune.biz.service.RespService;
import com.kangyonggan.app.fortune.model.annotation.CacheGetOrSave;
import com.kangyonggan.app.fortune.model.annotation.LogTime;
import com.kangyonggan.app.fortune.model.constants.AppConstants;
import com.kangyonggan.app.fortune.model.constants.TranSt;
import com.kangyonggan.app.fortune.model.vo.Resp;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * @author kangyonggan
 * @since 5/4/17
 */
@Service
@Log4j2
public class RespServiceImpl extends BaseService<Resp> implements RespService {

    @Override
    @LogTime
    @CacheGetOrSave("resp:respCo:{0}")
    public Resp findRespByRespCo(String respCo) {
        Resp resp = new Resp();
        resp.setIsDeleted(AppConstants.IS_DELETED_NO);
        resp.setRespCo(respCo);

        resp = super.selectOne(resp);

        if (resp == null) {
            log.info("错误码不存在或已逻辑删除:{}, 将使用保底错误码9999", respCo);

            resp = new Resp();
            resp.setRespCo("9999");
            resp.setRespMsg("未知异常");
            resp.setTransSt(TranSt.E.name());
        }

        return resp;
    }
}
