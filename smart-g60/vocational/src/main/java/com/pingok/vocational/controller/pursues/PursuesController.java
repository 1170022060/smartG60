package com.pingok.vocational.controller.pursues;

import com.pingok.vocational.domain.pursues.TblPursuesTrans;
import com.pingok.vocational.domain.roster.TblPursuesListRecord;
import com.pingok.vocational.service.pursues.IPursuesService;
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
 * 追讨功能
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/pursues")
public class PursuesController extends BaseController {
    @Autowired
    private IPursuesService pursuesService;

//    @RequiresPermissions("vocational:pursues:info")
    @Log(title = "追讨名单-分页查询", businessType = BusinessType.OTHER)
    @GetMapping("/info")
    public TableDataInfo info(@RequestParam(name = "startTime",required = false) Date startTime,
                              @RequestParam(name = "endTime",required = false) Date endTime,
                              @RequestParam(name = "vehPlate",required = false) String vehPlate)
    {
        startPage();
        List<Map> info = pursuesService.selectPursuesList(startTime,endTime,vehPlate);
        return getDataTable(info);
    }

    @RequiresPermissions("vocational:pursues:import")
    @Log(title = "追讨名单-导入", businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    public AjaxResult importData(MultipartFile file) throws Exception
    {
        ExcelUtil<TblPursuesListRecord> util = new ExcelUtil<TblPursuesListRecord>(TblPursuesListRecord.class);
        List<TblPursuesListRecord> pursuesList = util.importExcel(file.getInputStream());
        String message = pursuesService.importPursues(pursuesList);
        return AjaxResult.success(message);
    }

    @RequiresPermissions("vocational:pursues:importTemplate")
    @Log(title = "追讨名单-导入模板", businessType = BusinessType.IMPORT)
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException
    {
        ExcelUtil<TblPursuesListRecord> util = new ExcelUtil<TblPursuesListRecord>(TblPursuesListRecord.class);
        util.importTemplateExcel(response, "追讨名单");
    }

    @RequiresPermissions("vocational:pursues:function")
    @Log(title = "追讨", businessType = BusinessType.INSERT)
    @GetMapping("/function")
    public AjaxResult pursues(@RequestParam(name = "startTime",required = false) Date startTime, @RequestParam(name = "endTime",required = false) Date endTime)
    {
        pursuesService.Pursues(startTime,endTime);
        return AjaxResult.success();
    }

    @RequiresPermissions("vocational:pursues:trans")
    @Log(title = "追讨名单-流水查询", businessType = BusinessType.OTHER)
    @GetMapping("/trans")
    public TableDataInfo trans(@RequestParam(name = "concertId",required = false) String concertId)
    {
        startPage();
        List<Map> info = pursuesService.selectPursuesTrans(concertId);
        return getDataTable(info);
    }

    @RequiresPermissions("vocational:pursues:gantry")
    @Log(title = "追讨名单-门架查询", businessType = BusinessType.OTHER)
    @GetMapping("/gantry")
    public TableDataInfo gantry(@RequestParam(name = "concertId",required = false) String concertId)
    {
        startPage();
        List<Map> info = pursuesService.selectPursuesGantry(concertId);
        return getDataTable(info);
    }

    @RequiresPermissions("vocational:pursues:lpr")
    @Log(title = "追讨名单-牌识查询", businessType = BusinessType.OTHER)
    @GetMapping("/lpr")
    public TableDataInfo lpr(@RequestParam(name = "concertId",required = false) String concertId)
    {
        startPage();
        List<Map> info = pursuesService.selectPursuesLpr(concertId);
        return getDataTable(info);
    }

    @GetMapping("/test")
    public AjaxResult test(@RequestParam(name = "vehPlate",required = false) String vehPlate,@RequestParam(name = "time",required = false) Date time)
    {
        return AjaxResult.success(pursuesService.test(vehPlate,time));
    }
}
