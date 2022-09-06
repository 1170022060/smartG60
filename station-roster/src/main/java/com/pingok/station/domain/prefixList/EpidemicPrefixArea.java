package com.pingok.station.domain.prefixList;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

public class EpidemicPrefixArea implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long RecordId;

    /**
     * 车牌前缀
     */
    private String Prefix;

    /**
     * 生效时间
     */
    private Date StartTime;

    /**
     * 版本号
     */
    private String Version;

    public Long getRecordId() {
        return RecordId;
    }

    public void setRecordId(Long recordId) {
        RecordId = recordId;
    }

    public String getPrefix() {
        return Prefix;
    }

    public void setPrefix(String prefix) {
        Prefix = prefix;
    }

    public Date getStartTime() {
        return StartTime;
    }

    public void setStartTime(Date startTime) {
        StartTime = startTime;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }
}
