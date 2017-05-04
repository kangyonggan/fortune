package com.kangyonggan.app.fortune.web.controller.dashboard;

import com.kangyonggan.app.fortune.biz.service.MenuService;
import com.kangyonggan.app.fortune.biz.service.UserService;
import com.kangyonggan.app.fortune.model.vo.Menu;
import com.kangyonggan.app.fortune.model.vo.ShiroUser;
import com.kangyonggan.app.fortune.model.vo.User;
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
    private UserService userService;

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
        ShiroUser shiroUser = userService.getShiroUser();
        User user = userService.findUserByUsername(shiroUser.getUsername());
        List<Menu> menus = menuService.findMenusByUsername(shiroUser.getUsername());

        model.addAttribute("user", user);
        model.addAttribute("menus", menus);
        return "dashboard/layout";
    }

    @RequestMapping(value = "index", method = RequestMethod.GET)
    @RequiresPermissions("DASHBOARD")
    public String index() {
        return getPathRoot();
    }

}
