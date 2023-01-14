package com.pingok.vocational.controller.field;

import com.pingok.vocational.domain.field.TblFieldInfo;
import com.pingok.vocational.service.field.TblFieldInfoService;
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
 * 场地信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/field")
public class TblFieldInfoController extends BaseController {
    @Autowired
    private TblFieldInfoService tblFieldInfoService;

    @RequiresPermissions("vocational:field:idInfo")
    @Log(title = "场地信息-根据ID查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/idInfo")
    public AjaxResult idInfo(@RequestParam(name = "id") Long id)
    {
        TblFieldInfo idInfo = tblFieldInfoService.selectFieldInfoById(id);
        return AjaxResult.success(idInfo);
    }

//    @RequiresPermissions("vocational:field:info")
    @Log(title = "场地信息-分页查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/info")
    public TableDataInfo info(@RequestParam(name = "stationBelong",required = false) String stationBelong, @RequestParam(name = "roadBelong",required = false) String roadBelong, @RequestParam(name = "fieldName",required = false) String fieldName, @RequestParam(name = "type",required = false) Integer type,@RequestParam(name = "status",required = false) Integer status)
    {
        startPage();
        List<Map> info = tblFieldInfoService.selectFieldInfo(stationBelong, roadBelong,  fieldName,  type, status);
        return getDataTable(info);
    }

    @RequiresPermissions("vocational:field:add")
    @Log(title = "场地信息", businessType = BusinessType.INSERT) 
    @PostMapping
    public AjaxResult add(@Validated @RequestBody TblFieldInfo tblFieldInfo)
    {
        if (UserConstants.NOT_UNIQUE.equals(tblFieldInfoService.checkFieldNameUnique(tblFieldInfo)))
        {
            return AjaxResult.error("新增场地名'" + tblFieldInfo.getFieldName() + "'失败，场地名已存在");
        }
        return toAjax(tblFieldInfoService.insertFieldInfo(tblFieldInfo));
    }

    @RequiresPermissions("vocational:field:edit")
    @Log(title = "场地信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody TblFieldInfo tblFieldInfo)
    {
        if (UserConstants.NOT_UNIQUE.equals(tblFieldInfoService.checkFieldNameUnique(tblFieldInfo)))
        {
            return AjaxResult.error("修改场地名'" + tblFieldInfo.getFieldName() + "'失败，场地名已存在");
        }
        return toAjax(tblFieldInfoService.updateFieldInfo(tblFieldInfo));
    }

    @RequiresPermissions("vocational:field:status")
    @Log(title = "场地信息-状态改变", businessType = BusinessType.UPDATE)
    @PutMapping("/status")
    public AjaxResult status(@RequestParam(name = "id") Long id,@RequestParam(name = "status") Integer status)
    {
        return toAjax(tblFieldInfoService.updateStatus(id,status));
    }

    @GetMapping("/getFieldTreeMenu")
    public AjaxResult tree(){
        List<TblFieldInfo> menus = tblFieldInfoService.selectAll();
        return AjaxResult.success(tblFieldInfoService.fieldTreeMenu(menus));
    }
}
