package com.pingok.datacenter.domain.software;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 软件数据下载状态表(TBL_SOFTWARE_DOWNLOAD_STATUS)
 * 
 * @author qiumin
 * @version 1.0.0 2022-03-24
 */
@Data
@Table(name = "TBL_SOFTWARE_DOWNLOAD_STATUS")
public class TblSoftwareDownloadStatus  implements Serializable {

    /** id */
    @Id
    private Long id;

    /** 心跳ID */
    private Long heartbeatId;

    /** 数据类型：1-状态名单 2-追缴名单 3-绿通名单 4-抢险救灾名单 5-两客一危名单 6-其他 */
    private Integer type;

    /** 下载状态：1-正常 0-异常 */
    private Integer status;

    /** 状态描述 */
    private String statusCode;

    /** 下载数据版本号 */
    private String version;

    /** 下载时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;


}