package com.pingok.devicemonitor.domain.gantry.vo;

import lombok.Data;

import javax.persistence.Transient;
import java.util.List;

/**
 * @author
 * @time 2022/5/23 13:46
 */
@Data
public class ETCTUploadModel {
    @Transient
    private String msgId;
    @Transient
    private String msgTime;

    private String gantryId;
    private Integer computerOrder; //控制器序号 1-主机；2-备机
    private Integer notifyType; //通知类型
    private String startTime;
    private String endTime;
    private String hourBatchNo;
    private List<String> tradeIdList; //计费交易编号
}
