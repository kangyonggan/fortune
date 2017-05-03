package com.kangyonggan.app.fortune.web.controller.web;

import com.kangyonggan.app.fortune.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author kangyonggan
 * @since 2016/12/22
 */
@Controller
@RequestMapping("/")
public class IndexController extends BaseController {

    /**
     * 网站模板
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String web() {
        return "web/web";
    }

    /**
     * 网站首页
     *
     * @return
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index() {
        return "web/login/index";
    }

}
