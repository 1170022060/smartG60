package com.pingok.external.controller.gps;

import com.pingok.external.service.beiDou.IGpsService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 养护车辆GPS 信息操作处理
 *
 * @author qiumin
 */
@RestController
@RequestMapping("/gps")
public class GpsController extends BaseController {

    @Autowired
    private IGpsService iGpsService;

    @GetMapping()
    public AjaxResult getCarsGps() {
        iGpsService.getCarsGps();
        return AjaxResult.success();
    }

}
