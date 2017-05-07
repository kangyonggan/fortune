package com.kangyonggan.app.fortune.web.controller.web;

import com.kangyonggan.app.fortune.biz.service.MenuService;
import com.kangyonggan.app.fortune.biz.service.MerchantService;
import com.kangyonggan.app.fortune.biz.service.RoleService;
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

}