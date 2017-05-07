package com.kangyonggan.app.fortune.web.controller.web;

import com.kangyonggan.app.fortune.biz.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author kangyonggan
 * @since 2016/12/3
 */
@Controller
@RequestMapping("validate")
public class ValidateController {

    @Autowired
    private MerchantService merchantServicel;

    /**
     * 校验商户号是否可用
     *
     * @param merchCo
     * @param oldMerchCo
     * @return
     */
    @RequestMapping(value = "merchant", method = RequestMethod.POST)
    @ResponseBody
    public boolean validateUsername(@RequestParam("merchCo") String merchCo,
                                    @RequestParam(value = "oldMerchCo", required = false, defaultValue = "") String oldMerchCo) {
        if (merchCo.equals(oldMerchCo)) {
            return true;
        }

        return !merchantServicel.existsMerchCo(merchCo);
    }

}