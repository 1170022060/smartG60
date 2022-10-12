package com.pingok.event.domain.videoEvent;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 车流统计表(TBL_PARKING_STATISTICS)
 * 
 * @author xia
 * @version 1.0.0 2022-09-07
 */
@Data
@Table(name = "TBL_PARKING_STATISTICS")
public class TblParkingStatistics implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 5459353661272099808L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /** id */
    @Id
    private Long id;

    /** 场地id */
    private Long fieldId;

    /** 1：客车 2：货车 */
    private Integer vehType;

    /** 日期 */
    private Date day;

    /** 小时 */
    private Integer hour;

    /** 当前车辆数量 */
    private Integer currentNum;

    /** 驶入车辆数量 */
    private Integer enter;

    /** 驶出车辆数量 */
    private Integer out;


}