package com.pingok.datacenter.domain.gantry.model;

import lombok.Data;

import javax.persistence.Transient;

/**
 * @author
 * @time 2022/5/22 13:37
 */
@Data
public class GetErrorDataModel {
    @Transient
    private String msgId;
    @Transient
    private String msgTime;

    private String gantryId; //门架编码
    private Integer errorDataType; //异常数据类型，1-牌识 2-计费
    private Integer correctionResultType; //修正结果类型，1-未修复 2-已修复 3-不可修复
    private Integer pageIndex; //页码 分页索引值，从1开始，每页100条
}
