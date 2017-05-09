package com.kangyonggan.app.fortune.web.controller.dashboard;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.app.fortune.biz.service.CommandService;
import com.kangyonggan.app.fortune.biz.service.DictionaryService;
import com.kangyonggan.app.fortune.model.constants.DictionaryType;
import com.kangyonggan.app.fortune.model.dto.CommandDto;
import com.kangyonggan.app.fortune.model.vo.Command;
import com.kangyonggan.app.fortune.model.vo.Dictionary;
import com.kangyonggan.app.fortune.web.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("dashboard/merchant/command")
public class DashboardMerchantCommandController extends BaseController {

    @Autowired
    private CommandService commandService;

    @Autowired
    private DictionaryService dictionaryService;

    /**
     * 交易列表
     *
     * @param pageNum
     * @param startDate
     * @param endDate
     * @param tranSt
     * @param model
     * @return
     * @throws ParseException
     */
    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("MERCHANT_COMMAND")
    public String list(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNum,
                       @RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
                       @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
                       @RequestParam(value = "tranSt", required = false, defaultValue = "") String tranSt,
                       Model model) throws ParseException {
        List<Command> commands = commandService.searchCommands(pageNum, startDate.replaceAll("-", ""), endDate.replaceAll("-", ""), tranSt);
        PageInfo<Command> page = new PageInfo(commands);
        List<Dictionary> allTrans = dictionaryService.findDictionariesByType(DictionaryType.TRANS_CO.getType());

        model.addAttribute("allTrans", allTrans);
        model.addAttribute("page", page);
        return getPathList();
    }

    /**
     * 交易详情
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}", method = RequestMethod.GET)
    @RequiresPermissions("MERCHANT_COMMAND")
    public String detail(@PathVariable("id") Long id, Model model) {
        CommandDto commandDto = commandService.findCommandById(id);
        List<Dictionary> allTrans = dictionaryService.findDictionariesByType(DictionaryType.TRANS_CO.getType());
        List<Dictionary> allIdTps = dictionaryService.findDictionariesByType(DictionaryType.ID_TP.getType());
        List<Dictionary> allCurrs = dictionaryService.findDictionariesByType(DictionaryType.CURR_CO.getType());
        List<Dictionary> allAcctTps = dictionaryService.findDictionariesByType(DictionaryType.ACCT_TP.getType());

        model.addAttribute("command", commandDto);
        model.addAttribute("allTrans", allTrans);
        model.addAttribute("allIdTps", allIdTps);
        model.addAttribute("allCurrs", allCurrs);
        model.addAttribute("allAcctTps", allAcctTps);
        return getPathDetailModal();
    }

}
