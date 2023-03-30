package com.pingok.devicemonitor.domain.gantry.vo;

import lombok.Data;

import javax.persistence.Transient;
import java.util.List;

/**
 * @author
 * @time 2022/5/23 13:43
 */
@Data
public class VIPUploadModel {
    @Transient
    private String msgId;
    @Transient
    private String msgTime;

    private String gantryId;
    private List<String> picIdList; //牌识流水编号
}
