package com.pingok.datacenter.domain.trans.vo;

import lombok.Data;

@Data
public class UpdatePassIdVo {
    public Long recordId;
    public String tableName;
    public String passId;
}
