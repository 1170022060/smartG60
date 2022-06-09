package com.pingok.vocational.controller.software;

import com.pingok.vocational.domain.software.TblSoftwareInfo;
import com.pingok.vocational.service.software.ISoftwareInfoService;
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
 * 软件信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/software")
public class SoftwareInfoController extends BaseController {

    @Autowired
    private ISoftwareInfoService softwareInfoService;

    @RequiresPermissions("vocational:software:idInfo")
    @Log(title = "软件信息-根据ID查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/idInfo")
    public AjaxResult idInfo(@RequestParam(name = "id") Long id)
    {
        TblSoftwareInfo idInfo = softwareInfoService.selectSoftwareInfoById(id);
        return AjaxResult.success(idInfo);
    }

    @RequiresPermissions("vocational:software:info")
    @Log(title = "软件信息-分页查询", businessType = BusinessType.OTHER)
    @GetMapping("/info")
    public TableDataInfo info(@RequestParam(name = "stationId",required = false) String stationId,@RequestParam(name = "name",required = false) String name)
    {
        startPage();
        List<Map> info = softwareInfoService.selectSoftwareInfo(stationId,name);
        return getDataTable(info);
    }

    @RequiresPermissions("vocational:software:add")
    @Log(title = "软件信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody TblSoftwareInfo tblSoftwareInfo)
    {
        if (UserConstants.NOT_UNIQUE.equals(softwareInfoService.checkSoftNumUnique(tblSoftwareInfo)))
        {
            return AjaxResult.error("新增软件编码'" + tblSoftwareInfo.getNum() + "'失败，软件编码已存在");
        }
        return toAjax(softwareInfoService.insertSoftwareInfo(tblSoftwareInfo));
    }

    @RequiresPermissions("vocational:software:edit")
    @Log(title = "软件信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody TblSoftwareInfo tblSoftwareInfo)
    {
        if (UserConstants.NOT_UNIQUE.equals(softwareInfoService.checkSoftNumUnique(tblSoftwareInfo)))
        {
            return AjaxResult.error("修改软件编码'" + tblSoftwareInfo.getNum() + "'失败，软件编码已存在");
        }
        return toAjax(softwareInfoService.updateSoftwareInfo(tblSoftwareInfo));
    }
}
