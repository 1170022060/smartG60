package com.pingok.api.controller.beidou;

import com.pingok.api.service.beidou.IBeiDouService;
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
@RequestMapping("/beidou")
public class BeiDouController extends BaseController {

    @Autowired
    private IBeiDouService iBeiDouService;

    @GetMapping("/list")
    public AjaxResult list() {
        return AjaxResult.success(iBeiDouService.list());
    }


}
