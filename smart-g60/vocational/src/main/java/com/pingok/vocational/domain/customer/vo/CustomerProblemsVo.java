package com.pingok.vocational.domain.customer.vo;

import com.ruoyi.common.core.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * @author lal
 */
@Data
public class CustomerProblemsVo {
    @Excel(name = "回复人")
    private String replyUser;
    @Excel(name = "投诉开始时间")
    private Date startTime;
    @Excel(name = "投诉结束时间")
    private Date endTime;
    @Excel(name = "处理人部门")
    private Long handleDept;
}
