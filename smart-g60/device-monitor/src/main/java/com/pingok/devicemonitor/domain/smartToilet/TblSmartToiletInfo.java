package com.pingok.devicemonitor.domain.smartToilet;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author 
 * 智慧厕所表
 */
@Table(name="TBL_SMART_TOILET_INFO")
@Data
public class TblSmartToiletInfo implements Serializable {
    @Id
    private Long id;

    /**
     * 厕所序列号
     */
    private String serNum;

    /**
     * 厕所名称
     */
    private String serName;

    /**
     * 16（男厕）32（女厕）64（第三卫生间）
     */
    private Integer serType;

    /**
     * 坑位数
     */
    private Integer total;

    /**
     * 空闲坑位数
     */
    private Integer surplus;

    /**
     * 进入厕所人数
     */
    private Integer rateIn;

    /**
     * 离开厕所人数
     */
    private Integer rateOut;

    /**
     * 0 异常  1 正常
     */
    private Integer status;

    /**
     * 状态描述
     */
    private String statusDesc;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建用户ID
     */
    private Long createUserId;

    /**
     * 更新用户ID
     */
    private Long updateUserId;

    /**
     * 场地id
     */
    private Long fieldId;

    private static final long serialVersionUID = 1L;
}