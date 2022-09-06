package com.pingok.vocational.controller.roster;

import com.pingok.vocational.domain.roster.TblPrefixListRecord;
import com.pingok.vocational.service.roster.IPrefixListRecordService;
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

import java.util.Date;
import java.util.List;

/**
 * 中高风险地区车牌前缀名单
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/prefixList")
public class PrefixListRecordController extends BaseController {

    @Autowired
    private IPrefixListRecordService prefixListRecordService;

    @RequiresPermissions("vocational:prefixList:info")
    @Log(title = "中高风险地区车牌前缀名单-分页查询", businessType = BusinessType.OTHER)
    @GetMapping("/info")
    public TableDataInfo info(@RequestParam(name = "prefix",required = false) String prefix,@RequestParam(name = "startTime",required = false) Date startTime, @RequestParam(name = "endTime",required = false) Date endTime)
    {
        startPage();
        List<TblPrefixListRecord> info = prefixListRecordService.selectPrefixList(prefix ,startTime , endTime);
        return getDataTable(info);
    }
}
