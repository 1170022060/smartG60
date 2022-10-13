package com.pingok.monitor.controller.smartToilet;

import com.pingok.monitor.domain.smartToilet.TblSmartToiletSchedule;
import com.pingok.monitor.service.smartToilet.ISmartToiletScheduleService;
import com.pingok.monitor.service.smartToilet.ISmartToiletService;
import com.ruoyi.common.core.constant.UserConstants;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 智慧厕所 信息操作处理
 *
 * @author qiumin
 */
@RestController
@RequestMapping("/smartToilet")
public class SmartToiletController extends BaseController {

    @Autowired
    private ISmartToiletService iSmartToiletService;
    @Autowired
    private ISmartToiletScheduleService iSmartToiletScheduleService;

//    @RequiresPermissions("monitor:smartToilet:findByFieldNum")
//    @Log(title = "智慧厕所监控服务", businessType = BusinessType.OTHER)
    @GetMapping
    public AjaxResult findByFieldNum(@RequestParam String fieldNum,Date workDate) {
        return AjaxResult.success(iSmartToiletService.findByFieldNum(fieldNum,workDate));
    }

    /**
     * 查询厕所排班记录
     * @param fieldId
     * @param workDate
     * @return
     */
    @GetMapping("/list")
    public TableDataInfo list(Long fieldId,Long toiletId, Date workDate){
        startPage();
        List<Map> info = iSmartToiletScheduleService.findToiletScheduleList(fieldId,toiletId,workDate);
        return getDataTable(info);
    }

    /**
     * 新增厕所排班
     * @param tblSmartToiletSchedule
     * @return
     */
//    @RequiresPermissions("monitor:smartToilet:add")
    @Log(title = "新增厕所排班",businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody TblSmartToiletSchedule tblSmartToiletSchedule) {
        return toAjax(iSmartToiletScheduleService.insert(tblSmartToiletSchedule));
    }

    /**
     * 编辑厕所排班
     * @param tblSmartToiletSchedule
     * @return
     */
    @RequiresPermissions("monitor:smartToilet:edit")
    @Log(title = "编辑厕所排班",businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody TblSmartToiletSchedule tblSmartToiletSchedule){
        return toAjax(iSmartToiletScheduleService.update(tblSmartToiletSchedule));
    }

    @GetMapping("/getToiletType")
    public AjaxResult getToiletType(){
        List<Map> info = iSmartToiletScheduleService.getToiletType();
        return AjaxResult.success(info);
    }

    @GetMapping("/findById")
    public AjaxResult findById(@RequestParam Long id){
        return AjaxResult.success(iSmartToiletScheduleService.findById(id));
    }
}
