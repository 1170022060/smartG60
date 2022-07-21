package com.pingok.vocational.controller.roadDoctor;

import com.pingok.vocational.service.roadDoctor.IRoadDoctorService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 道路医生
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/roadDoctor")
public class RoadDoctorController extends BaseController {

    @Autowired
    private IRoadDoctorService iRoadDoctorService;


    /**
     * 获取列表
     *
     * @return
     */
//    @RequiresPermissions("vocational:roadDoctor:search")
//    @Log(title = "道路病害管理", businessType = BusinessType.OTHER)
    @GetMapping("/search")
    public TableDataInfo search(String questName, String pZhuangHao, Date startTime, Date endTime) {
        startPage();
        List<Map> list = iRoadDoctorService.list(questName, pZhuangHao, startTime, endTime);
        return getDataTable(list);
    }

}
