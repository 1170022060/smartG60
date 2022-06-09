package com.pingok.vocational.controller.roster;

import com.pingok.vocational.service.roster.IRecoveryListRecordService;
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
 * 追缴名单
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/recoveryList")
public class RecoveryListRecordController extends BaseController {

    @Autowired
    private IRecoveryListRecordService tblRecoveryListRecordService;

    @RequiresPermissions("vocational:recoveryList:info")
    @Log(title = "追缴名单-分页查询", businessType = BusinessType.OTHER)
    @GetMapping("/info")
    public TableDataInfo info(@RequestParam(name = "vehPlate",required = false) String vehPlate)
    {
        startPage();
        List<Map> info = tblRecoveryListRecordService.selectRecoveryList(vehPlate);
        return getDataTable(info);
    }
}
