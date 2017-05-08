package com.kangyonggan.app.fortune.web.controller.dashboard;

import com.kangyonggan.app.fortune.biz.service.DictionaryService;
import com.kangyonggan.app.fortune.biz.service.MerchAcctService;
import com.kangyonggan.app.fortune.biz.service.MerchantService;
import com.kangyonggan.app.fortune.model.constants.DictionaryType;
import com.kangyonggan.app.fortune.model.vo.Dictionary;
import com.kangyonggan.app.fortune.model.vo.MerchAcct;
import com.kangyonggan.app.fortune.model.vo.ShiroMerchant;
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
 * @since 5/8/17
 */
@Controller
@RequestMapping("dashboard/merchant/acct")
public class DashboardMerchantAcctController extends BaseController {

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private MerchAcctService merchAcctService;

    @Autowired
    private DictionaryService dictionaryService;

    /**
     * 账户列表
     *
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("MERCHANT_ACCT")
    public String list(Model model) {
        ShiroMerchant shiroMerchant = merchantService.getShiroMerchant();
        List<MerchAcct> merchAccts = merchAcctService.findMerAcctByMerchCo(shiroMerchant.getMerchCo());
        List<Dictionary> idTps = dictionaryService.findDictionariesByType(DictionaryType.ID_TP.getType());

        model.addAttribute("merchAccts", merchAccts);
        model.addAttribute("idTps", idTps);
        return getPathList();
    }

    /**
     * 添加银行卡
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    @RequiresPermissions("MERCHANT_ACCT")
    public String create(Model model) {
        model.addAttribute("merchAcct", new MerchAcct());
        List<Dictionary> idTps = dictionaryService.findDictionariesByType(DictionaryType.ID_TP.getType());

        model.addAttribute("idTps", idTps);
        return getPathFormModal();
    }

    /**
     * 保存银行卡
     *
     * @param merchAcct
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("MERCHANT_ACCT")
    public Map<String, Object> save(@ModelAttribute("merchAcct") @Valid MerchAcct merchAcct, BindingResult result) {
        Map<String, Object> resultMap = getResultMap();
        ShiroMerchant shiroMerchant = merchantService.getShiroMerchant();
        merchAcct.setMerchCo(shiroMerchant.getMerchCo());
        merchAcct.setBalance(null);

        if (!result.hasErrors()) {
            merchAcctService.saveMerchAcct(merchAcct);
        } else {
            setResultMapFailure(resultMap);
        }

        return resultMap;
    }

    /**
     * 编辑商户
     *
     * @param merchAcctNo
     * @param model
     * @return
     */
    @RequestMapping(value = "{merchAcctNo:[\\w]+}/edit", method = RequestMethod.GET)
    @RequiresPermissions("MERCHANT_ACCT")
    public String create(@PathVariable("merchAcctNo") String merchAcctNo, Model model) {
        List<Dictionary> idTps = dictionaryService.findDictionariesByType(DictionaryType.ID_TP.getType());

        model.addAttribute("idTps", idTps);
        model.addAttribute("merchAcct", merchAcctService.findMerAcctByMerchCoAndAcctNo(merchantService.getShiroMerchant().getMerchCo(), merchAcctNo));
        return getPathFormModal();
    }

    /**
     * 更新商户
     *
     * @param merchAcct
     * @param result
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("MERCHANT_ACCT")
    public Map<String, Object> update(@ModelAttribute("merchAcct") @Valid MerchAcct merchAcct, BindingResult result) {
        Map<String, Object> resultMap = getResultMap();
        ShiroMerchant shiroMerchant = merchantService.getShiroMerchant();
        merchAcct.setMerchCo(shiroMerchant.getMerchCo());
        merchAcct.setBalance(null);

        if (!result.hasErrors()) {
            merchAcctService.updateMerchAcct(merchAcct);
        } else {
            setResultMapFailure(resultMap);
        }

        return resultMap;
    }

    /**
     * 删除
     *
     * @param merchAcctNo
     * @return
     */
    @RequestMapping(value = "{merchAcct:[\\w]+}/delete", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    @RequiresPermissions("MERCHANT_ACCT")
    @ResponseBody
    public void delete(@PathVariable("merchAcct") String merchAcctNo) {
        MerchAcct merchAcct = new MerchAcct();
        ShiroMerchant shiroMerchant = merchantService.getShiroMerchant();
        merchAcct.setMerchCo(shiroMerchant.getMerchCo());
        merchAcct.setMerchAcctNo(merchAcctNo);

        merchAcctService.deleteMerchAcct(merchAcct);
    }
}
