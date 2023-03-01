package com.pingok.event.domain.buildManage;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author lal
 */
@Data
@Table(name = "TBL_BUILD_MANAGE")
public class TblBuildManage implements Serializable {

    /**
     * 主键
     */
    @Id
    private Long id;
    /**
     * 施工内容
     */
    private String content;
    /**
     * 开始桩号
     */
    private String startPileNum;
    /**
     * 结束桩号
     */
    private String endPileNum;
    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date startTime;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 创建人id
     */
    private Long createUserId;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 更新人id
     */
    private Long updateUserId;
}
