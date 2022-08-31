package com.pingok.external.controller.roadDoctor;

import com.pingok.external.service.roadDoctor.IRoadDoctorService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.InnerAuth;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 道路医生 信息操作处理
 *
 * @author qiumin
 */
@RestController
@RequestMapping("/roadDoctor")
public class RoadDoctorController extends BaseController {

    @Autowired
    private IRoadDoctorService iRoadDoctorService;

    @InnerAuth
    @PostMapping()
    public AjaxResult updateDisease() {
        iRoadDoctorService.updateDisease();
        return AjaxResult.success();
    }
    @RequiresPermissions("external-system:roadDoctor:search")
    @Log(title = "道路病害管理", businessType = BusinessType.OTHER)
    @GetMapping("/search")
    public TableDataInfo search(String questName, String pZhuangHao, Date startTime, Date endTime) {
        startPage();
        List<Map> list = iRoadDoctorService.list(questName, pZhuangHao, startTime, endTime);
        return getDataTable(list);
    }

    @RequiresPermissions("external-system:roadDoctor:push")
    @Log(title = "道路病害推送", businessType = BusinessType.UPDATE)
    @PutMapping("/push")
    public AjaxResult push(@RequestParam(name = "id") Long id) {
        return toAjax(iRoadDoctorService.push(id));
    }
}
