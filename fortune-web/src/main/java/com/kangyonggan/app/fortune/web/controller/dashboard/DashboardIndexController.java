package com.kangyonggan.app.fortune.web.controller.dashboard;

import com.kangyonggan.app.fortune.biz.service.MenuService;
import com.kangyonggan.app.fortune.biz.service.MerchantService;
import com.kangyonggan.app.fortune.model.vo.Menu;
import com.kangyonggan.app.fortune.model.vo.Merchant;
import com.kangyonggan.app.fortune.model.vo.ShiroMerchant;
import com.kangyonggan.app.fortune.web.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2016/12/22
 */
@Controller
@RequestMapping("dashboard")
public class DashboardIndexController extends BaseController {

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private MenuService menuService;

    /**
     * 工作台
     *
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("DASHBOARD")
    public String layout(Model model) {
        ShiroMerchant shiroMerchant = merchantService.getShiroMerchant();
        Merchant merchant = merchantService.findMerchantByMerchCo(shiroMerchant.getMerchCo());
        List<Menu> menus = menuService.findMenusByMerchCo(shiroMerchant.getMerchCo());

        model.addAttribute("merchant", merchant);
        model.addAttribute("menus", menus);
        return "dashboard/layout";
    }

    @RequestMapping(value = "index", method = RequestMethod.GET)
    @RequiresPermissions("DASHBOARD")
    public String index() {
        return getPathRoot();
    }

}
