package com.kangyonggan.app.fortune.web.controller.web;

import com.kangyonggan.app.fortune.biz.service.*;
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

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private MerchAcctService merchAcctService;

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

    /**
     * 校验角色代码是否可用
     *
     * @param code
     * @param oldCode
     * @return
     */
    @RequestMapping(value = "role", method = RequestMethod.POST)
    @ResponseBody
    public boolean validateRoleCode(@RequestParam("code") String code,
                                    @RequestParam(value = "oldCode", required = false, defaultValue = "") String oldCode) {
        if (code.equals(oldCode)) {
            return true;
        }

        return !roleService.existsRoleCode(code);
    }

    /**
     * 校验菜单代码是否可用
     *
     * @param code
     * @param oldCode
     * @return
     */
    @RequestMapping(value = "menu", method = RequestMethod.POST)
    @ResponseBody
    public boolean validateMenuCode(@RequestParam("code") String code,
                                    @RequestParam(value = "oldCode", required = false, defaultValue = "") String oldCode) {
        if (code.equals(oldCode)) {
            return true;
        }

        return !menuService.existsMenuCode(code);
    }

    /**
     * 校验字典代码是否可用
     *
     * @param code
     * @param oldCode
     * @return
     */
    @RequestMapping(value = "dictionary", method = RequestMethod.POST)
    @ResponseBody
    public boolean validateDictionaryCode(@RequestParam("code") String code,
                                          @RequestParam(value = "oldCode", required = false, defaultValue = "") String oldCode) {
        if (code.equals(oldCode)) {
            return true;
        }

        return !dictionaryService.existsDictionaryCode(code);
    }

    /**
     * 校验卡号是否可用
     *
     * @param merchAcctNo
     * @param oldMerchAcctNo
     * @return
     */
    @RequestMapping(value = "acct", method = RequestMethod.POST)
    @ResponseBody
    public boolean validateMerchAcctNo(@RequestParam("merchAcctNo") String merchAcctNo,
                                          @RequestParam(value = "oldMerchAcctNo", required = false, defaultValue = "") String oldMerchAcctNo) {
        if (merchAcctNo.equals(oldMerchAcctNo)) {
            return true;
        }

        return !merchAcctService.existsMerchAcctNo(merchAcctNo);
    }

}