package com.pingok.vocational.controller.infoboard;

import com.pingok.vocational.service.infoboard.IInfoBoardService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vms")
public class InfoBoardController extends BaseController {

    @Autowired
    private IInfoBoardService iInfoBoardService;

    @Log(title = "情报板列表查询（按类型分组）", businessType = BusinessType.OTHER)
    @GetMapping("/getListByType")
    public AjaxResult getListByType(@RequestParam(name="type", required = false) String type) {
        return AjaxResult.success(iInfoBoardService.getListByType(type));
    }
}
