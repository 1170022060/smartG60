package com.pingok.monitor.domain.smartToilet;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 厕所排班表
 */
@Data
@Table(name = "TBL_SMART_TOILET_SCHEDULE")
public class TblSmartToiletSchedule implements Serializable {
    @Id
    private Long id;

    private Long fieldId;

    private Long toiletId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date workDate;

    private String toiChief;

    private String workCleanerAm;

    private String workCleanerPm;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 创建用户ID */
    private Long createUserId;

    /** 更新用户ID */
    private Long updateUserId;
}
