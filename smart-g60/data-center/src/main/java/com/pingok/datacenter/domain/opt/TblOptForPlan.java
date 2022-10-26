package com.pingok.datacenter.domain.opt;

import lombok.Data;

import javax.persistence.Id;

@Data
public class TblOptForPlan {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
    private Long id;

    /** 员工ID */
    private Integer optId;

    /** 员工姓名 */
    private String optName;
}
