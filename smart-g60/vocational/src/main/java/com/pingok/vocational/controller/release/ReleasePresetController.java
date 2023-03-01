package com.pingok.vocational.controller.release;

import com.pingok.vocational.domain.release.TblReleasePreset;
import com.pingok.vocational.domain.release.vo.ReleasePresetEnum;
import com.pingok.vocational.service.release.IReleasePresetService;
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
 * 信息发布预设
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/releasePreset")
public class ReleasePresetController extends BaseController {

    @Autowired
    private IReleasePresetService releasePresetService;

//    @RequiresPermissions("vocational:releasePreset:publish")
    @Log(title = "信息发布", businessType = BusinessType.OTHER)
    @PostMapping(value="/publish")
    public AjaxResult publish(@RequestBody ReleasePresetEnum releasePresetEnum)
    {
        return AjaxResult.success();
    }

//    @RequiresPermissions("vocational:releasePreset:idInfo")
    @Log(title = "信息发布预设信息-根据ID查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/idInfo")
    public AjaxResult idInfo(@RequestParam(name = "id") Long id)
    {
        TblReleasePreset idInfo = releasePresetService.selectReleasePresetById(id);
        return AjaxResult.success(idInfo);
    }

//    @RequiresPermissions("vocational:releasePreset:info")
    @Log(title = "信息发布预设信息-分页查询", businessType = BusinessType.OTHER)
    @GetMapping("/info")
    public TableDataInfo info(@RequestParam(name = "infoType",required = false) Integer infoType,@RequestParam(name = "status",required = false) Integer status,@RequestParam(name = "presetName",required = false) String presetName)
    {
        startPage();
        List<Map> info = releasePresetService.selectReleasePreset(infoType,status,presetName);
        return getDataTable(info);
    }

//    @RequiresPermissions("vocational:releasePreset:deviceInfo")
    @Log(title = "信息发布预设信息-适合发送的设备信息查询", businessType = BusinessType.OTHER)
    @GetMapping("/deviceInfo")
    public TableDataInfo info(@RequestParam(name = "id") Long id)
    {
        startPage();
        List<Map> info = releasePresetService.selectDevice(id);
        return getDataTable(info);
    }


    @RequiresPermissions("vocational:releasePreset:add")
    @Log(title = "信息发布预设信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody TblReleasePreset tblReleasePreset)
    {
        if (UserConstants.NOT_UNIQUE.equals(releasePresetService.checkPresetInfoUnique(tblReleasePreset)))
        {
            return AjaxResult.error("新增预设信息'" + tblReleasePreset.getPresetInfo() + "'失败，预设信息已存在");
        }
        return toAjax(releasePresetService.insertReleasePreset(tblReleasePreset));
    }

    @RequiresPermissions("vocational:releasePreset:edit")
    @Log(title = "信息发布预设信息", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public AjaxResult edit(@Validated @RequestBody TblReleasePreset tblReleasePreset)
    {
        if (UserConstants.NOT_UNIQUE.equals(releasePresetService.checkPresetInfoUnique(tblReleasePreset)))
        {
            return AjaxResult.error("修改预设信息'" + tblReleasePreset.getPresetInfo() + "'失败，预设信息已存在");
        }
        return toAjax(releasePresetService.updateReleasePreset(tblReleasePreset));
    }

    @RequiresPermissions("vocational:releasePreset:status")
    @Log(title = "信息发布预设信息-状态改变", businessType = BusinessType.UPDATE)
    @PutMapping("/status")
    public AjaxResult status(@RequestParam(name = "id") Long id,@RequestParam(name = "status") Integer status)
    {
        return toAjax(releasePresetService.updateStatus(id,status));
    }
}
