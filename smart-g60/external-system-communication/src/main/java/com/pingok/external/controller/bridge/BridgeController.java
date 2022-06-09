package com.pingok.external.controller.bridge;

import com.pingok.external.service.bridge.IBridgeService;
import com.pingok.external.service.bridge.IBridgeTemperatureService;
import com.pingok.external.service.gps.IGpsService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 桥梁 信息操作处理
 *
 * @author qiumin
 */
@RestController
@RequestMapping("/bridge")
public class BridgeController extends BaseController {

    @Autowired
    private IBridgeTemperatureService iBridgeTemperatureService;
    @Autowired
    private IBridgeService iBridgeService;

    @GetMapping("/getWarning")
    public AjaxResult getWarning() {
        iBridgeService.getWarning();
        return AjaxResult.success();
    }

    @GetMapping("/updateAcquisition")
    public AjaxResult updateAcquisition() {
        iBridgeService.updateAcquisition();
        return AjaxResult.success();
    }

    @GetMapping("/updateBridgeInfo")
    public AjaxResult updateBridgeInfo() {
        iBridgeService.updateBridgeInfo();
        return AjaxResult.success();
    }

    @GetMapping("/updateCollection")
    public AjaxResult updateCollection() {
        iBridgeService.updateCollection();
        return AjaxResult.success();
    }

    @GetMapping("/updateProjectInfo")
    public AjaxResult updateProjectInfo() {
        iBridgeService.updateProjectInfo();
        return AjaxResult.success();
    }

    @GetMapping("/updateWarning")
    public AjaxResult updateWarning() {
        iBridgeService.updateWarning();
        return AjaxResult.success();
    }

    @GetMapping("/getTemperature")
    public AjaxResult getTemperature() {
        iBridgeTemperatureService.getTemperature();
        return AjaxResult.success();
    }

}
