package com.pingok.datacenter.domain.gantry.model;

import lombok.Data;

import javax.persistence.Transient;

/**
 * @author
 * @time 2022/5/23 10:31
 */
@Data
public class FixErrorDataModel {
    @Transient
    private String msgId;
    @Transient
    private String msgTime;

    private String gantryId;
    private String errorId;
    private Integer errorDataType;
    private String errorJson;
    private String correctExplain;
    private Integer correctionResultType;
    private Integer dataSouceType; //数据来源类型 1-工控机 2-后台
}
