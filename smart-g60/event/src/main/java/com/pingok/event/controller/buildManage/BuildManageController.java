package com.pingok.event.controller.buildManage;

import com.pingok.event.domain.buildManage.TblBuildManage;
import com.pingok.event.service.buildManage.IBuildManageService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author lal
 */
@RestController
@RequestMapping("/buildManage")
public class BuildManageController extends BaseController {

    @Autowired
    IBuildManageService iBuildManageService;

    @GetMapping
    public TableDataInfo getInfo(@RequestParam(required = false) String content, @RequestParam(required = false) Date startTime,@RequestParam(required = false) Date endTime){
        startPage();
        List<Map> list = iBuildManageService.getBuilManaInfo(content,startTime,endTime);
        return getDataTable(list);
    }

    @RequiresPermissions("event:buildManage:add")
    @Log(title = "新增施工管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TblBuildManage tblBuildManage){
        return toAjax(iBuildManageService.add(tblBuildManage));
    }

//    @RequiresPermissions("event:buildManage:edit")
    @Log(title = "编辑施工管理", businessType = BusinessType.INSERT)
    @PutMapping
    public AjaxResult edit(@RequestBody TblBuildManage tblBuildManage){
        return toAjax(iBuildManageService.edit(tblBuildManage));
    }
}
