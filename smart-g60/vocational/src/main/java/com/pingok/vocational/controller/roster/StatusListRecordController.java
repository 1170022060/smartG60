package com.pingok.vocational.controller.roster;

import com.pingok.vocational.service.roster.IStatusListRecordService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 状态名单
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/statusList")
public class StatusListRecordController extends BaseController {
    @Autowired
    private IStatusListRecordService tblStatusListRecordService;

    @RequiresPermissions("vocational:statusList:info")
    @Log(title = "状态名单-分页查询", businessType = BusinessType.OTHER)
    @GetMapping("/info")
    public TableDataInfo info(@RequestParam(name = "cardId",required = false) String cardId)
    {
        startPage();
        List<Map> info = tblStatusListRecordService.selectStatusList(cardId);
        return getDataTable(info);
    }
}
