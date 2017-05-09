package com.kangyonggan.app.fortune.web.controller.dashboard;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.app.fortune.biz.service.MerchantService;
import com.kangyonggan.app.fortune.biz.service.RoleService;
import com.kangyonggan.app.fortune.common.util.Collections3;
import com.kangyonggan.app.fortune.model.constants.AppConstants;
import com.kangyonggan.app.fortune.model.vo.Merchant;
import com.kangyonggan.app.fortune.model.vo.Role;
import com.kangyonggan.app.fortune.web.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author kangyonggan
 * @since 2017/1/8
 */
@Controller
@RequestMapping("dashboard/system/merchant")
public class DashboardSystemMerchantController extends BaseController {

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private RoleService roleService;

    /**
     * 商户管理
     *
     * @param pageNum
     * @param merchCo
     * @param merchNm
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("SYSTEM_MERCHANT")
    public String index(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNum,
                        @RequestParam(value = "merchCo", required = false, defaultValue = "") String merchCo,
                        @RequestParam(value = "merchNm", required = false, defaultValue = "") String merchNm,
                        Model model) {
        List<Merchant> merchants = merchantService.searchMerchants(pageNum, merchCo, merchNm);
        PageInfo<Merchant> page = new PageInfo(merchants);

        model.addAttribute("page", page);
        return getPathList();
    }

    /**
     * 添加商户
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    @RequiresPermissions("SYSTEM_MERCHANT")
    public String create(Model model) {
        model.addAttribute("merchant", new Merchant());
        return getPathFormModal();
    }

    /**
     * 保存商户
     *
     * @param merchant
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("SYSTEM_MERCHANT")
    public Map<String, Object> save(@ModelAttribute("merchant") @Valid Merchant merchant, BindingResult result) {
        Map<String, Object> resultMap = getResultMap();
        if (!result.hasErrors()) {
            merchantService.saveMerchantWithDefaultRole(merchant);
        } else {
            setResultMapFailure(resultMap);
        }

        return resultMap;
    }

    /**
     * 编辑商户
     *
     * @param merchCo
     * @param model
     * @return
     */
    @RequestMapping(value = "{merchCo:[\\w]+}/edit", method = RequestMethod.GET)
    @RequiresPermissions("SYSTEM_MERCHANT")
    public String create(@PathVariable("merchCo") String merchCo, Model model) {
        model.addAttribute("merchant", merchantService.findMerchantByMerchCo(merchCo));
        return getPathFormModal();
    }

    /**
     * 更新商户
     *
     * @param merchant
     * @param result
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("SYSTEM_MERCHANT")
    public Map<String, Object> update(@ModelAttribute("merchant") @Valid Merchant merchant, BindingResult result) {
        Map<String, Object> resultMap = getResultMap();
        if (!result.hasErrors()) {
            merchantService.updateMerchantByMerchCo(merchant);
        } else {
            setResultMapFailure(resultMap);
        }

        return resultMap;
    }

    /**
     * 删除
     *
     * @param merchCo
     * @param model
     * @return
     */
    @RequestMapping(value = "{merchCo:[\\w]+}/delete", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    @RequiresPermissions("SYSTEM_MERCHANT")
    public String delete(@PathVariable("merchCo") String merchCo, Model model) {
        Merchant merchant = merchantService.findMerchantByMerchCo(merchCo);
        merchant.setIsDeleted(AppConstants.IS_DELETED_YES);
        merchantService.updateMerchantByMerchCo(merchant);

        model.addAttribute("merchant", merchant);
        return getPathTableTr();
    }

    /**
     * 恢复
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/undelete", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    @RequiresPermissions("SYSTEM_MERCHANT")
    public String delete(@PathVariable("id") Long id, Model model) {
        Merchant merchant = merchantService.findMerchantById(id);
        merchant.setIsDeleted(AppConstants.IS_DELETED_NO);
        merchantService.updateMerchantByMerchCo(merchant);

        model.addAttribute("merchant", merchant);
        return getPathTableTr();
    }

    /**
     * 商户详情
     *
     * @param merchCo
     * @param model
     * @return
     */
    @RequestMapping(value = "{merchCo:[\\w]+}", method = RequestMethod.GET)
    @RequiresPermissions("SYSTEM_MERCHANT")
    public String detail(@PathVariable("merchCo") String merchCo, Model model) {
        model.addAttribute("merchant", merchantService.findMerchantByMerchCo(merchCo));
        return getPathDetailModal();
    }

    /**
     * 修改密码
     *
     * @param merchCo
     * @param model
     * @return
     */
    @RequestMapping(value = "{merchCo:[\\w]+}/password", method = RequestMethod.GET)
    @RequiresPermissions("SYSTEM_MERCHANT")
    public String password(@PathVariable("merchCo") String merchCo, Model model) {
        model.addAttribute("merchant", merchantService.findMerchantByMerchCo(merchCo));
        return getPathRoot() + "/password-modal";
    }

    /**
     * 修改密码
     *
     * @param merchant
     * @param result
     * @return
     */
    @RequestMapping(value = "password", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("SYSTEM_MERCHANT")
    public Map<String, Object> password(@ModelAttribute("merchant") @Valid Merchant merchant, BindingResult result) {
        Map<String, Object> resultMap = getResultMap();
        if (!result.hasErrors()) {
            merchantService.updateMerchantPassword(merchant);
        } else {
            setResultMapFailure(resultMap);
        }

        return resultMap;
    }

    /**
     * 设置角色
     *
     * @param merchCo
     * @param model
     * @return
     */
    @RequestMapping(value = "{merchCo:[\\w]+}/roles", method = RequestMethod.GET)
    @RequiresPermissions("SYSTEM_MERCHANT")
    public String roles(@PathVariable("merchCo") String merchCo, Model model) {
        Merchant merchant = merchantService.findMerchantByMerchCo(merchCo);
        List<Role> merchantRoles = roleService.findRolesByMercoCo(merchCo);
        merchantRoles = Collections3.extractToList(merchantRoles, "code");
        List<Role> all_roles = roleService.findAllRoles();

        model.addAttribute("merchCo", merchCo);
        model.addAttribute("merchant_roles", merchantRoles);
        model.addAttribute("all_roles", all_roles);
        return getPathRoot() + "/roles-modal";
    }

    /**
     * 保存角色
     *
     * @param merchCo
     * @param roles
     * @return
     */
    @RequestMapping(value = "{merchCo:[\\w]+}/roles", method = RequestMethod.POST)
    @RequiresPermissions("SYSTEM_MERCHANT")
    @ResponseBody
    public Map<String, Object> updateUserRoles(@PathVariable(value = "merchCo") String merchCo,
                                               @RequestParam(value = "roles", defaultValue = "") String roles) {
        Merchant merchant = merchantService.findMerchantByMerchCo(merchCo);
        merchantService.updateMerchanrRoles(merchCo, roles);

        return getResultMap();
    }

}
