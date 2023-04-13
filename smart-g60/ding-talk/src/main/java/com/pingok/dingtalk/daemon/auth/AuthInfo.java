package com.pingok.dingtalk.daemon.auth;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author chenwg
 */
@Data
public class AuthInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String tenant_id;

    private String user_id;

    private String dept_id;

    private String post_id;

    private String role_id;

    private List<String> oauth_id;

    private String account;

    private String user_name;

    private String nick_name;

    private String role_name;

    private String avatar;

    private String access_token;

    private String refresh_token;

    private String token_type;

    private int expires_in;

    private String license;

    private String ding_user_id;
}
