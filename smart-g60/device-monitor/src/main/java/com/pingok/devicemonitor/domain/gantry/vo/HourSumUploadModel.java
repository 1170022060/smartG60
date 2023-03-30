package com.pingok.devicemonitor.domain.gantry.vo;

import lombok.Data;

import javax.persistence.Transient;
import java.util.List;

/**
 * @author
 * @time 2022/5/23 13:49
 */
@Data
public class HourSumUploadModel {
    @Transient
    private String msgId;
    @Transient
    private String msgTime;

    private String gantryId;
    private Integer collectType; //汇总类型 1-交易 2-牌识
    private List<String> collectHourBatchList; //小时批次号
}
