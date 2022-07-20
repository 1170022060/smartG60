package com.pingok.external.controller.roadDoctor;

import com.pingok.external.service.roadDoctor.IRoadDoctorService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping()
    @Log(title = "道路医生", businessType = BusinessType.INSERT)
    public AjaxResult updateDisease() {
        iRoadDoctorService.updateDisease();
        return AjaxResult.success();
    }

}
