package com.pingok.external.controller.dingTalk;

import com.pingok.external.service.dingTalk.IDingTalkService;
import com.pingok.external.service.gps.IGpsService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 养护车辆GPS 信息操作处理
 *
 * @author qiumin
 */
@RestController
@RequestMapping("/dingTalk")
public class DingTalkController extends BaseController {

    @Autowired
    private IDingTalkService iDingTalkService;

    @GetMapping("/updateDepartmentList")
    public AjaxResult updateDepartmentList() {
        iDingTalkService.updateDepartmentList(null);
        return AjaxResult.success();
    }

}
