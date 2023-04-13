package com.pingok.dingtalk.service.auth;

import com.pingok.dingtalk.daemon.auth.AuthInfo;

public interface AuthService {

    /**
     * 获取钉钉授权token
     *
     * @return
     */
    String getAuthToken();
}
