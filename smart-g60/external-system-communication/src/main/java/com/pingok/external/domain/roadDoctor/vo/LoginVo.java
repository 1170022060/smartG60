package com.pingok.external.domain.roadDoctor.vo;

import lombok.Data;

/**
 * 登录信息
 */
@Data
public class LoginVo {
    private String  token;
    private Integer ExiresInMinute;
    private Long BeginDate;
    private String CompanyName;
}
