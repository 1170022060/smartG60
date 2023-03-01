package com.pingok.datacenter.domain.opt;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "TBL_OPT_ID")
public class TblOptId {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
    private Integer id;

    /** 员工姓名 */
    private String optName;

    public Integer specialty;
    public Integer hisTimeType;
    public Integer timeType;
    public Integer hisWorkId7;
    public Integer hisWorkId19;
    public Integer lastWorkId7;
    public Integer lastWorkId19;

}
