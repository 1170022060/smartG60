package com.pingok.datacenter.domain.opt;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "TBL_OPT_WORK_DETAIL")
public class TblOptWorkDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
    private Long id;

    /** 员工ID */
    private Integer optId;

    /** 岗位代码 */
    private Integer workId;

    /** 岗位阶段 */
    private Integer workStep;

    /** 阶段时长 */
    private Integer workMinutes;

    /** 排班日期 */
    private Date workDate;

    /** 车道/保洁区 */
    private Integer laneNum;

    /** 历史岗位代码 */
    private Integer hisWorkId;
}
