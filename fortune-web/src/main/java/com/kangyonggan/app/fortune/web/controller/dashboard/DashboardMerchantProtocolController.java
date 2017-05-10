package com.kangyonggan.app.fortune.web.controller.dashboard;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.app.fortune.biz.service.DictionaryService;
import com.kangyonggan.app.fortune.biz.service.ProtocolService;
import com.kangyonggan.app.fortune.model.constants.DictionaryType;
import com.kangyonggan.app.fortune.model.vo.Command;
import com.kangyonggan.app.fortune.model.vo.Dictionary;
import com.kangyonggan.app.fortune.model.vo.Protocol;
import com.kangyonggan.app.fortune.web.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.util.List;

/**
 * @author kangyonggan
 * @since 5/8/17
 */
@Controller
@RequestMapping("dashboard/merchant/protocol")
public class DashboardMerchantProtocolController extends BaseController {

    @Autowired
    private ProtocolService protocolService;

    @Autowired
    private DictionaryService dictionaryService;

    /**
     * 协议列表
     *
     * @param pageNum
     * @param startDate
     * @param endDate
     * @param protocolNo
     * @param acctNo
     * @param model
     * @return
     * @throws ParseException
     */
    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("MERCHANT_PROTOCOL")
    public String list(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNum,
                       @RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
                       @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
                       @RequestParam(value = "protocolNo", required = false, defaultValue = "") String protocolNo,
                       @RequestParam(value = "acctNo", required = false, defaultValue = "") String acctNo,
                       Model model) throws ParseException {
        List<Protocol> protocols = protocolService.searchProtocols(pageNum, startDate, endDate, protocolNo, acctNo);
        PageInfo<Command> page = new PageInfo(protocols);
        List<Dictionary> idTps = dictionaryService.findDictionariesByType(DictionaryType.ID_TP.getType());

        model.addAttribute("idTps", idTps);
        model.addAttribute("page", page);
        return getPathList();
    }


}
