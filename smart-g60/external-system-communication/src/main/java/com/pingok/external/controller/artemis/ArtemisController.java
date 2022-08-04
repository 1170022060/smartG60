package com.pingok.external.controller.artemis;

import com.pingok.external.service.artemis.IArtemisService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 海康 信息操作处理
 *
 * @author qiumin
 */
@RestController
@RequestMapping("/artemis")
public class ArtemisController extends BaseController {

    @Autowired
    private IArtemisService iArtemisService;

    @GetMapping()
    public AjaxResult updateStatus() {
        iArtemisService.updateStatus();
        return AjaxResult.success();
    }
}
