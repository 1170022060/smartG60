package com.ruoyi.system.api.domain.algorithm;

import lombok.Data;

/** 车道平均速度
 * @author
 * @time 2022/6/8 9:48
 */
@Data
public class LaneAvgSpeed {

    private String stationNum; //桩号
    private String lineNum; //车道号
    private Integer lineType; //车道类型 0:查询车道 1:相邻车道
    private String lineSpeed; //车道速度
    private Integer limitSpeed; //限速

}
