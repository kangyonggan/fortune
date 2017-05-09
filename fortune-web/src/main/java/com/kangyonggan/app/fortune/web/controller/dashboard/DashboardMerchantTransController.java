package com.kangyonggan.app.fortune.web.controller.dashboard;

import com.kangyonggan.app.fortune.biz.service.DictionaryService;
import com.kangyonggan.app.fortune.biz.service.MerchantService;
import com.kangyonggan.app.fortune.biz.service.TransService;
import com.kangyonggan.app.fortune.model.constants.AppConstants;
import com.kangyonggan.app.fortune.model.constants.DictionaryType;
import com.kangyonggan.app.fortune.model.vo.Dictionary;
import com.kangyonggan.app.fortune.model.vo.ShiroMerchant;
import com.kangyonggan.app.fortune.model.vo.Trans;
import com.kangyonggan.app.fortune.web.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author kangyonggan
 * @since 5/8/17
 */
@Controller
@RequestMapping("dashboard/merchant/trans")
public class DashboardMerchantTransController extends BaseController {

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private TransService transService;

    @Autowired
    private DictionaryService dictionaryService;

    /**
     * 交易类型列表
     *
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("MERCHANT_TRANS")
    public String list(Model model) {
        ShiroMerchant shiroMerchant = merchantService.getShiroMerchant();
        List<Trans> transs = transService.findTransByMerchCo(shiroMerchant.getMerchCo());

        model.addAttribute("transs", transs);
        return getPathList();
    }

    /**
     * 添加交易类型
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    @RequiresPermissions("MERCHANT_TRANS")
    public String create(Model model) {
        ShiroMerchant shiroMerchant = merchantService.getShiroMerchant();
        List<Trans> transs = transService.findTransByMerchCo(shiroMerchant.getMerchCo());
        List<Dictionary> allTrans = dictionaryService.findDictionariesByType(DictionaryType.TRANS_CO.getType());
        allTrans = filter(allTrans, transs);

        model.addAttribute("allTrans", allTrans);
        return getPathFormModal();
    }

    /**
     * 保存交易类型
     *
     * @param trans
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("MERCHANT_TRANS")
    public Map<String, Object> save(@ModelAttribute("trans") @Valid Trans trans, BindingResult result) {
        Map<String, Object> resultMap = getResultMap();
        ShiroMerchant shiroMerchant = merchantService.getShiroMerchant();
        trans.setMerchCo(shiroMerchant.getMerchCo());

        if (!result.hasErrors()) {
            transService.saveTrans(trans);
        } else {
            setResultMapFailure(resultMap);
        }

        return resultMap;
    }

    /**
     * 编辑交易类型
     *
     * @param tranCo
     * @param model
     * @return
     */
    @RequestMapping(value = "{tranCo:[\\w]+}/edit", method = RequestMethod.GET)
    @RequiresPermissions("MERCHANT_TRANS")
    public String create(@PathVariable("tranCo") String tranCo, Model model) {
        ShiroMerchant shiroMerchant = merchantService.getShiroMerchant();
        Trans trans = transService.findTransByMerchCoAndTranCo(shiroMerchant.getMerchCo(), tranCo);

        model.addAttribute("trans", trans);
        return getPathFormModal();
    }

    /**
     * 更新交易类型
     *
     * @param trans
     * @param result
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("MERCHANT_TRANS")
    public Map<String, Object> update(@ModelAttribute("trans") @Valid Trans trans, BindingResult result) {
        Map<String, Object> resultMap = getResultMap();
        ShiroMerchant shiroMerchant = merchantService.getShiroMerchant();
        trans.setMerchCo(shiroMerchant.getMerchCo());

        if (!result.hasErrors()) {
            transService.updateTransByMerchCoAndTranCo(trans);
        } else {
            setResultMapFailure(resultMap);
        }

        return resultMap;
    }

    /**
     * 删除
     *
     * @param tranCo
     * @param model
     * @return
     */
    @RequestMapping(value = "{tranCo:[\\w]+}/delete", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    @RequiresPermissions("SYSTEM_MERCHANT")
    public String delete(@PathVariable("tranCo") String tranCo, Model model) {
        ShiroMerchant shiroMerchant = merchantService.getShiroMerchant();
        Trans trans = new Trans();
        trans.setMerchCo(shiroMerchant.getMerchCo());
        trans.setTranCo(tranCo);
        trans.setIsDeleted(AppConstants.IS_DELETED_YES);
        transService.updateTransByMerchCoAndTranCo(trans);

        model.addAttribute("trans", transService.findTransByMerchCoAndTranCo(shiroMerchant.getMerchCo(), tranCo));
        return getPathTableTr();
    }

    /**
     * 恢复
     *
     * @param tranCo
     * @param model
     * @return
     */
    @RequestMapping(value = "{tranCo:[\\w]+}/undelete", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    @RequiresPermissions("SYSTEM_MERCHANT")
    public String undelete(@PathVariable("tranCo") String tranCo, Model model) {
        ShiroMerchant shiroMerchant = merchantService.getShiroMerchant();
        Trans trans = new Trans();
        trans.setMerchCo(shiroMerchant.getMerchCo());
        trans.setTranCo(tranCo);
        trans.setIsDeleted(AppConstants.IS_DELETED_NO);
        transService.updateTransByMerchCoAndTranCo(trans);

        model.addAttribute("trans", transService.findTransByMerchCoAndTranCo(shiroMerchant.getMerchCo(), tranCo));
        return getPathTableTr();
    }

    /**
     * 物理删除
     *
     * @param tranCo
     * @return
     */
    @RequestMapping(value = "{tranCo:[\\w]+}/remove", method = RequestMethod.GET)
    @RequiresPermissions("MERCHANT_TRANS")
    @ResponseBody
    public void remove(@PathVariable("tranCo") String tranCo) {
        Trans trans = new Trans();
        ShiroMerchant shiroMerchant = merchantService.getShiroMerchant();
        trans.setMerchCo(shiroMerchant.getMerchCo());
        trans.setTranCo(tranCo);

        transService.deleteTrans(trans);
    }

    private List<Dictionary> filter(List<Dictionary> allTrans, List<Trans> transs) {
        List<Dictionary> newList = new ArrayList();

        for (Dictionary dictionary : allTrans) {
            boolean exists = false;
            for (Trans trans : transs) {
                if (trans.getTranCo().equals(dictionary.getCode())) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                newList.add(dictionary);
            }
        }
        return newList;
    }
}
