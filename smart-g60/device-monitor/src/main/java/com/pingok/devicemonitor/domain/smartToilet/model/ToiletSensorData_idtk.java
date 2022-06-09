package com.pingok.devicemonitor.domain.smartToilet.model;

import lombok.Data;

/**
 * @author
 * @time 2022/5/16 12:37
 */
@Data
public class ToiletSensorData_idtk {
    private Integer rate_in; //进入厕所人数
    private Integer rate_out; //离开厕所人数
    private Integer focus; //聚焦状态例(1:正常，2：异常)
}
