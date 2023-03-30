package com.pingok.internetAuth.service;

import com.ruoyi.common.core.constant.Constants;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.enums.UserStatus;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.ServletUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.ip.IpUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteInternetLogService;
import com.ruoyi.system.api.RemoteInternetUserService;
import com.ruoyi.system.api.domain.internet.InternetLogininfor;
import com.ruoyi.system.api.domain.internet.InternetUser;
import com.ruoyi.system.api.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 登录校验方法
 *
 * @author ruoyi
 */
@Component
public class SysLoginService {
    @Autowired
    private RemoteInternetLogService remoteLogService;

    @Autowired
    private RemoteInternetUserService remoteUserService;

    /**
     * 登录
     */
    public LoginUser login(String username, String password) {
        // 用户名或密码为空 错误
        if (StringUtils.isAnyBlank(username, password)) {
            recordLogininfor(username, Constants.LOGIN_FAIL, "appKey/appSecret不能为空");
            throw new ServiceException("appKey/appSecret不能为空");
        }
//        // 密码如果不在指定范围内 错误
//        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
//                || password.length() > UserConstants.PASSWORD_MAX_LENGTH)
//        {
//            recordLogininfor(username, Constants.LOGIN_FAIL, "用户密码不在指定范围");
//            throw new ServiceException("用户密码不在指定范围");
//        }
//        // 用户名不在指定范围内 错误
//        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
//                || username.length() > UserConstants.USERNAME_MAX_LENGTH)
//        {
//            recordLogininfor(username, Constants.LOGIN_FAIL, "用户名不在指定范围");
//            throw new ServiceException("用户名不在指定范围");
//        }
        // 查询用户信息
        R<LoginUser> userResult = remoteUserService.selectByUserName(username, SecurityConstants.INNER);

        if (R.FAIL == userResult.getCode()) {
            throw new ServiceException(userResult.getMsg());
        }

        if (StringUtils.isNull(userResult) || StringUtils.isNull(userResult.getData())) {
            recordLogininfor(username, Constants.LOGIN_FAIL, "appKey错误");
            throw new ServiceException("appKey：" + username + " 错误");
        }
        LoginUser userInfo = userResult.getData();
        InternetUser user = userResult.getData().getInternetUser();
        if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
            recordLogininfor(username, Constants.LOGIN_FAIL, "对不起，您的appKey已停用");
            throw new ServiceException("对不起，appKey：" + username + " 已停用");
        }
        if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            recordLogininfor(username, Constants.LOGIN_FAIL, "appKey已停用，请联系管理员");
            throw new ServiceException("对不起，appKey：" + username + " 已停用");
        }
        if (!SecurityUtils.matchesPassword(password, user.getPassword())) {
            recordLogininfor(username, Constants.LOGIN_FAIL, "appSecret错误");
            throw new ServiceException("appSecret不存在/错误");
        }
        recordLogininfor(username, Constants.LOGIN_SUCCESS, "成功");
        return userInfo;
    }

    public void logout(String loginName) {
        recordLogininfor(loginName, Constants.LOGOUT, "成功");
    }


    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息内容
     * @return
     */
    public void recordLogininfor(String username, String status, String message) {
        InternetLogininfor logininfor = new InternetLogininfor();
        logininfor.setUserName(username);
        logininfor.setIpaddr(IpUtils.getIpAddr(ServletUtils.getRequest()));
        logininfor.setMsg(message);
        // 日志状态
        if (StringUtils.equalsAny(status, Constants.LOGIN_SUCCESS, Constants.LOGOUT, Constants.REGISTER)) {
            logininfor.setStatus("0");
        } else if (Constants.LOGIN_FAIL.equals(status)) {
            logininfor.setStatus("1");
        }
        remoteLogService.saveLogininfor(logininfor, SecurityConstants.INNER);
    }
}