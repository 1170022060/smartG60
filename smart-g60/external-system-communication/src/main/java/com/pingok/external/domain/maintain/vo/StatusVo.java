package com.pingok.external.domain.maintain.vo;

import lombok.Data;

@Data
public class StatusVo {
    private String token;
    private String ordernum;
    private String status;
    private String username;
    private Long pdate;
    private String picurl;
    private String note;
}
