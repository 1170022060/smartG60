package com.pingok.vocational.domain.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author
 * @time 2022/5/30 17:36
 */
@Data
@Table(name = "TBL_OPT_INFO")
public class TblOptInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    private Integer optId;

    private String optName;

    private String belongStation;

    private String belongCenter;

    private String password;

    private String remark;

    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private Long createUserId;

    private Long updateUserId;

    private Integer issuseStatus;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date issuseTime;
}
