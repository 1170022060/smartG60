package com.pingok.external.controller.user;

import com.pingok.external.service.user.IInternetUserService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.security.annotation.InnerAuth;
import com.ruoyi.system.api.domain.internet.InternetUser;
import com.ruoyi.system.api.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 第三方系统授权
 *
 * @author
 */
@RestController
@RequestMapping("/internetUser")
public class InternetUserController extends BaseController {
    @Autowired
    private IInternetUserService iInternetUserService;

    @InnerAuth
    @GetMapping("/selectByUserName")
    public R<LoginUser> selectByUserName(@RequestParam String userName) {
        InternetUser internetUser = iInternetUserService.selectByUserName(userName);
        if (StringUtils.isNull(internetUser))
        {
            return R.fail("appKey或appSecret错误");
        }
        LoginUser LoginUser = new LoginUser();
        LoginUser.setInternetUser(internetUser);
        return R.ok(LoginUser);
    }
}
