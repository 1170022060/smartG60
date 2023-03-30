package com.pingok.monitor.domain.smartToilet;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 * 智慧厕所厕位情况表
 */
@Table(name="TBL_SMART_TOILET_CUBICLE")
@Data
public class TblSmartToiletCubicle implements Serializable {
    @Id
    private Long id;

    /**
     * 厕所ID
     */
    private Integer serId;

    /**
     * 厕位编号
     */
    private Integer indexId;

    /**
     * 0 (未占用) 1(占用) 2 超时 3 故障
     */
    private Integer status;

    /**
     * 报警状态（0：无报警 其他：报警）
     */
    private Integer alarm;

    /**
     * 位置
     */
    private Integer position;

    private static final long serialVersionUID = 1L;

    private Date updateTime;

    private Long updateUserId;

    private String remark;

    private Integer alarmStatus;

}