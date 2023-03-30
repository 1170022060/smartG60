package com.pingok.external.controller.log;

import com.pingok.external.service.log.IInternetLogininforService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.security.annotation.InnerAuth;
import com.ruoyi.system.api.domain.internet.InternetLogininfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 第三方系统访问记录
 *
 * @author
 */
@RestController
@RequestMapping("/internetLoginInfor")
public class InternetLogininforController extends BaseController {
    @Autowired
    private IInternetLogininforService logininforService;


    @InnerAuth
    @PostMapping
    public AjaxResult add(@RequestBody InternetLogininfor logininfor) {
        return toAjax(logininforService.insertLogininfor(logininfor));
    }
}
