package com.pingok.vocational.controller.baseinfo;

import com.pingok.vocational.domain.baseinfo.TblPolicyRecord;
import com.pingok.vocational.service.baseinfo.IPolicyRecordService;
import com.ruoyi.common.core.constant.UserConstants;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 收费政策记录
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/policyRecord")
public class PolicyRecordController extends BaseController {

    @Autowired
    private IPolicyRecordService policyRecordService;

    @RequiresPermissions("vocational:policyRecord:idInfo")
    @Log(title = "收费政策信息-根据ID查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/idInfo")
    public AjaxResult idInfo(@RequestParam(name = "id") Long id)
    {
        TblPolicyRecord idInfo = policyRecordService.selectPolicyRecordById(id);
        return AjaxResult.success(idInfo);
    }

    @RequiresPermissions("vocational:policyRecord:info")
    @Log(title = "收费政策信息-分页查询", businessType = BusinessType.OTHER)
    @GetMapping("/info")
    public TableDataInfo info(@RequestParam(name = "title",required = false) String title,@RequestParam(name = "status",required = false) Integer status)
    {
        startPage();
        List<Map> info = policyRecordService.selectPolicyRecord(title,status);
        return getDataTable(info);
    }


    @RequiresPermissions("vocational:policyRecord:add")
    @Log(title = "收费政策信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody TblPolicyRecord tblPolicyRecord)
    {
        if (UserConstants.NOT_UNIQUE.equals(policyRecordService.checkTitleUnique(tblPolicyRecord)))
        {
            return AjaxResult.error("新增标题'" + tblPolicyRecord.getTitle() + "'失败，标题已存在");
        }
        return toAjax(policyRecordService.insertPolicyRecord(tblPolicyRecord));
    }

    @RequiresPermissions("vocational:policyRecord:edit")
    @Log(title = "收费政策信息", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public AjaxResult edit(@Validated @RequestBody TblPolicyRecord tblPolicyRecord)
    {
        if (UserConstants.NOT_UNIQUE.equals(policyRecordService.checkTitleUnique(tblPolicyRecord)))
        {
            return AjaxResult.error("修改标题'" + tblPolicyRecord.getTitle() + "'失败，标题已存在");
        }
        return toAjax(policyRecordService.updatePolicyRecord(tblPolicyRecord));
    }

    @RequiresPermissions("vocational:policyRecord:status")
    @Log(title = "收费政策信息-状态改变", businessType = BusinessType.UPDATE)
    @PutMapping("/status")
    public AjaxResult status(@RequestParam(name = "id") Long id,@RequestParam(name = "status") Integer status)
    {
        return toAjax(policyRecordService.updateStatus(id,status));
    }
}
