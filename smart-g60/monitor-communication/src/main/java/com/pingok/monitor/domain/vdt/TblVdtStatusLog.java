package com.pingok.monitor.domain.vdt;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @author
 * @time 2022/7/6 13:40
 */
@Data
public class TblVdtStatusLog implements Serializable {
    @Id
    private Long id;

    private String vdtId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String details;

    private static final long serialVersionUID = 1L;
}
