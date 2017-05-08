package com.kangyonggan.app.fortune.web.controller.dashboard;

import com.kangyonggan.app.fortune.biz.service.MerchantService;
import com.kangyonggan.app.fortune.model.vo.Merchant;
import com.kangyonggan.app.fortune.model.vo.ShiroMerchant;
import com.kangyonggan.app.fortune.web.controller.BaseController;
import com.kangyonggan.app.fortune.web.util.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author kangyonggan
 * @since 5/8/17
 */
@Controller
@RequestMapping("dashboard/merchant/info")
public class DashboardMerchantInfoController extends BaseController {

    @Autowired
    private MerchantService merchantService;

    /**
     * 基本信息
     *
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("MERCHANT_INFO")
    public String info(Model model) {
        ShiroMerchant shiroMerchant = merchantService.getShiroMerchant();
        Merchant merchant = merchantService.findMerchantByMerchCo(shiroMerchant.getMerchCo());

        model.addAttribute("merchant", merchant);
        return getPathIndex();
    }

    /**
     * 基本信息
     *
     * @param merchant
     * @param result
     * @param publicKey
     * @return
     * @throws FileUploadException
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("MERCHANT_INFO")
    public Map<String, Object> info(@ModelAttribute(value = "merchant") @Valid Merchant merchant, BindingResult result,
                                    @RequestParam(value = "publicKey", required = false) MultipartFile publicKey) throws FileUploadException {
        Map<String, Object> resultMap = getResultMap();
        ShiroMerchant shiroMerchant = merchantService.getShiroMerchant();

        if (!result.hasErrors()) {
            if (publicKey != null && !publicKey.isEmpty()) {
                String fileName = FileUpload.upload(publicKey);
                merchant.setPublicKeyPath(fileName);
            }

            merchant.setMerchCo(shiroMerchant.getMerchCo());
            merchantService.updateMerchantByMerchCo(merchant);
            resultMap.put("merchant", merchantService.findMerchantByMerchCo(merchant.getMerchCo()));
        } else {
            setResultMapFailure(resultMap);
        }

        return resultMap;
    }

}
