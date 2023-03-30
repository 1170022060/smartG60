package com.pingok.devicemonitor.domain.gantry.vo;

import lombok.Data;

import javax.persistence.Transient;
import java.util.List;

/**
 * @author
 * @time 2022/5/23 12:56
 */
@Data
public class VIUploadModel {
    @Transient
    private String msgId;
    @Transient
    private String msgTime;

    private String gantryId;
    private Integer notifyType; //通知类型
    private String startTime;
    private String endTime;
    private String hourBatchNo; //小时批次号
    private List<String> picIdList; //牌识流水编号
}
