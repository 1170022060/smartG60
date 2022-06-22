package com.pingok.vocational.controller.roster;

import com.pingok.vocational.domain.roster.TblPursuesListRecord;
import com.pingok.vocational.service.roster.IPursuesListRecordService;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 追讨名单
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/pursuesList")
public class PursuesListRecordController extends BaseController {
    @Autowired
    private IPursuesListRecordService pursuesListRecordService;

    @RequiresPermissions("vocational:pursuesList:info")
    @Log(title = "追讨名单-分页查询", businessType = BusinessType.OTHER)
    @GetMapping("/info")
    public TableDataInfo info(@RequestParam(name = "startTime",required = false) Date startTime, @RequestParam(name = "endTime",required = false) Date endTime)
    {
        startPage();
        List<Map> info = pursuesListRecordService.selectPursuesList(startTime,endTime);
        return getDataTable(info);
    }

    @RequiresPermissions("vocational:pursuesList:import")
    @Log(title = "追讨名单-导入", businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    public AjaxResult importData(MultipartFile file) throws Exception
    {
        ExcelUtil<TblPursuesListRecord> util = new ExcelUtil<TblPursuesListRecord>(TblPursuesListRecord.class);
        List<TblPursuesListRecord> pursuesList = util.importExcel(file.getInputStream());
        String message = pursuesListRecordService.importPursues(pursuesList);
        return AjaxResult.success(message);
    }

    @RequiresPermissions("vocational:pursuesList:importTemplate")
    @Log(title = "追讨名单-导入模板", businessType = BusinessType.IMPORT)
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException
    {
        ExcelUtil<TblPursuesListRecord> util = new ExcelUtil<TblPursuesListRecord>(TblPursuesListRecord.class);
        util.importTemplateExcel(response, "追讨名单");
    }
}
